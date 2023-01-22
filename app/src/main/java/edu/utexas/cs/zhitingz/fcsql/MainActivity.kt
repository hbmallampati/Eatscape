package edu.utexas.cs.zhitingz.fcsql

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import edu.utexas.cs.zhitingz.fcsql.databinding.ActivityMainBinding
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var restaurantDb: SQLiteDatabase
    private lateinit var dbHelper: DatabaseHelper

    private var restaurantAdapter: RestaurantItemAdapter? = null
    private lateinit var binding : ActivityMainBinding

    private val cities: Array<String> by lazy {
        resources.getStringArray(R.array.city)
    }
    private val restaurantTypes: Array<String> by lazy {
        resources.getStringArray(R.array.restaurant_type)
    }
    private val order: Array<String> by lazy {
        resources.getStringArray(R.array.order)
    }
    private val limit: Array<String> by lazy {
        resources.getStringArray(R.array.limit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.restaurant_type,
                android.R.layout.simple_spinner_item)
        restaurantTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.restaurantTypeSpinner.adapter = restaurantTypeAdapter

        val cityAdapter = ArrayAdapter.createFromResource(this,
                R.array.city,
                android.R.layout.simple_spinner_item)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.citySpinner.adapter = cityAdapter

        val limitAdapter = ArrayAdapter.createFromResource(this,
                R.array.limit,
                android.R.layout.simple_spinner_item)
        limitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.limitSpinner.adapter = limitAdapter

        val orderAdapter = ArrayAdapter.createFromResource(this,
                R.array.order,
                android.R.layout.simple_spinner_item)
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.chooseOrderSpinner.adapter = orderAdapter


        binding.queryButton.setOnClickListener(this)

        dbHelper = DatabaseHelper(this)

        try {
            dbHelper.createDatabase()
        } catch (e: IOException) {
            Log.e("DB", "Fail to create database", e)
        }

        restaurantDb = dbHelper.readableDatabase
    }

    override fun onClick(v: View) {
        Log.d(javaClass.simpleName, "on click ")

        // where contains the selection clause and args contains the corresponding arguments
        val where = ArrayList<String>()
        val args = ArrayList<String>()

        // XXX WRITE ME: Create table string that is the name of
        // the table we are querying in the database
        //TODO: var table = ""
        var table = "businesses"

        var columnsP : Array<String>? = null
        //var columnsP = arrayOf<String>("_id", "name", "full_address", "phone", "url", "price", "city")
        var selectionP :String? = null
        var selectionargsP :Array<String>? = null
        var orderbyP :String? = null
        var limitP :String? = null


        // XXX WRITE ME: Generate selection clause for query city and restaurant type
        // Please use query instead of rawQuery.
        // There are two helper function you can use handleCity and handleRestaurant.
        // The query for restaurant type is provided as an example.

        handleCity(where, args)
        table = handleRestaurant(table, where, args)

        var selectionStr = ""
        if (where.size != 0) {
            selectionStr += where[0]
            for (i in 1 until where.size) {
                selectionStr += " AND " + where[i]
            }
        }

        selectionP = selectionStr
        selectionargsP = args.toTypedArray()


        // XXX WRITE ME: Handle ORDER BY and LIMIT request
        when {
            binding.limitSpinner.selectedItemPosition > 0 -> limitP = limit[binding.limitSpinner.selectedItemPosition].lowercase()
        }

        if(binding.priceOrderCheckbox.isChecked)
            when {
                binding.chooseOrderSpinner.selectedItemPosition > 0 -> orderbyP =
                    "price" + " " + order[binding.chooseOrderSpinner.selectedItemPosition].lowercase()
            }


        // XXX WRITE ME: query database and show result in the ListView.
        // Look at the documentation for SQLiteDatabase.query
        // https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.html#query
        // You can pass null to columns, groupBy and having
        // If the query result is empty, generate a toast.

        val cursor = restaurantDb.query(table, columnsP, selectionP, selectionargsP,
            null, null, orderbyP, limitP)

        val count = cursor.count
        Log.d(javaClass.simpleName, "coursor count : "+ count)

        restaurantAdapter = RestaurantItemAdapter(this, cursor, false)
        binding.restaurantList.adapter = restaurantAdapter

        //cursor.close()

    }

    // Helper method for generate selection clause for query city
    private fun handleCity(where: MutableList<String>, args: MutableList<String>) {
        // XXX Write me.
        val cityNamePos = binding.citySpinner.selectedItemPosition
        if(cityNamePos != 0)
        {
            var cityFilter = ""
            when {
                cityNamePos > 0 -> cityFilter = cities[cityNamePos]
            }
            where.add("(businesses.city = ?)")
            args.add(cityFilter)
        }
    }

    // Helper method to generate the selection clause for the restaurant type.
    private fun handleRestaurant(tableStr: String, where: MutableList<String>, args: MutableList<String>): String
    {
        var table = tableStr
        val restaurantTypePos = binding.restaurantTypeSpinner.selectedItemPosition
        if (restaurantTypePos != 0)
        {
            var categoryFilter = ""
            when
            {
                restaurantTypePos == 1 -> categoryFilter = "newamerican"
                restaurantTypePos == 2 -> categoryFilter = "breakfast_brunch"
                // These are lower cased in the database, but cities are not
                restaurantTypePos > 0 -> categoryFilter = restaurantTypes[restaurantTypePos].lowercase()
            }
            table += ", categories"
            where.add("(businesses._id = categories._id) AND (categories.category_name = ?)")
            args.add(categoryFilter)
        }
        return table
    }
}

package com.suhel.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.suhel.cryptoapp.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var rvAdapter: RvAdapter
    lateinit var data:ArrayList<Modal>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data=ArrayList<Modal>()
        rvAdapter= RvAdapter(this,data)

        apiData
        binding.rcView.adapter=rvAdapter
        binding.rcView.layoutManager=LinearLayoutManager(this)

        binding.search.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val filterData=ArrayList<Modal>()
                for (item in data){
                    if (item.name?.lowercase(Locale.getDefault())?.contains(p0.toString().lowercase(Locale.getDefault())) == true){
                        filterData.add(item)
                    }
                    else{
                        rvAdapter.changeData(filterData)
                    }
                }
            }

        })
    }

    val apiData:Unit
        get() {
            val url="https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"

            val requestQueue=Volley.newRequestQueue(this)
            val jsonObjectRequest:JsonObjectRequest=object:JsonObjectRequest(Method.GET,url,null,
                Response.Listener {
                    response ->
                    binding.pgBar.isVisible=false
                    try {
                        val dataArray=response.getJSONArray("data")
                        for (i in 0 until dataArray.length()){
                            val dataObject=dataArray.getJSONObject(i)
                            val name=dataObject.getString("name")
                            val symbol=dataObject.getString("symbol")
                            val quote=dataObject.getJSONObject("quote")
                            val usd=quote.getJSONObject("USD")
                            val price=String.format("$ "+"%.2f",usd.getDouble("price"))
                            data.add(Modal(name,symbol,price.toString()))
                        }
                        rvAdapter.notifyDataSetChanged()
                    }catch (e:Exception){
                        Toast.makeText(this, "Error to fetch data", Toast.LENGTH_LONG).show()
                    }

                }, Response.ErrorListener {
                    Toast.makeText(this,it.localizedMessage ,Toast.LENGTH_LONG).show()
                }
            ){
                override fun getHeaders(): Map<String, String> {
                  val headers= HashMap<String,String>()
                    headers["X-CMC_PRO_API_KEY"]="a2fdff45-7025-44f2-840d-919d0a3ca38d"
                    return headers
                }
            }

            requestQueue.add(jsonObjectRequest)
        }


}
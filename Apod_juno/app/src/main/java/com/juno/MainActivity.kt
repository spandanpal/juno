package com.juno

import android.app.DatePickerDialog

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.juno.databinding.ActivityMainBinding
import com.juno.retrofit.ApiInterface
import com.juno.retrofit.Constant
import com.juno.retrofit.RetrofitError
import com.juno.retrofit.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null


    private val c = Calendar.getInstance()
    private var zoomOut = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.ivCalender?.setOnClickListener(View.OnClickListener { v ->
            showDatePicker(v)
        })

       // binding?.ivUrl?.setOnTouchListener(this)
        binding?.btZoom?.setOnClickListener(View.OnClickListener {v ->
            zoomOut = if (zoomOut) {
                binding?.ivUrl?.setLayoutParams(
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
                binding?.ivUrl?.setAdjustViewBounds(true)
                false
            } else {
                binding?.ivUrl?.setLayoutParams(
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                )
                binding?.ivUrl?.setScaleType(ImageView.ScaleType.FIT_XY)
                true
            }
        })
        getDetails()
    }



    private fun showDatePicker(v: View?) {
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, monthOfYear)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat)
                getDescription(sdf.format(c.time))
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis();
        datePickerDialog.show()
    }

    private fun getDescription(date: String) {
        QCProgressDialog.showProgressDialog(this, "LOADING_MESSAGE")
        val service: ApiInterface = RetrofitUtil.retrofit(Constant.BASE_URL)!!
        val call: Call<DateResponse?>? = service.getDateDescription("DEMO_KEY", date)
        call?.enqueue(object : Callback<DateResponse?> {
            override fun onResponse(
                call: Call<DateResponse?>?,
                response: Response<DateResponse?>
            ) {
                if (response.isSuccessful) {
                    try {
                        val r: DateResponse? = response.body()

                        binding?.tvTitle?.text = r?.getTitle()
                        binding?.tvDescription?.text = r?.getExplanation()

                        if (r?.getMediaType().equals("image")){
                            Glide.with(baseContext).load(r?.getHdurl()).dontAnimate()
                                .listener(object : RequestListener<Drawable?> {
                                    override fun onLoadFailed(
                                        e: GlideException?,
                                        model: Any,
                                        target: Target<Drawable?>,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        binding?.ivUrl?.visibility = View.GONE
                                        return false
                                    }

                                    override fun onResourceReady(
                                        resource: Drawable?,
                                        model: Any,
                                        target: Target<Drawable?>,
                                        dataSource: DataSource,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        binding?.ivUrl?.visibility = View.VISIBLE
                                        return false
                                    }
                                }).into(binding?.ivUrl!!)
                        } else if (r?.getMediaType().equals("video")){

                        }



                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                QCProgressDialog.hideProgressDialog()
            }


            override fun onFailure(call: Call<DateResponse?>, t: Throwable) {
                QCProgressDialog.hideProgressDialog()
                val str: String = RetrofitError.showErrorMessage(t)!!

            }
        })
    }

    private fun getDetails() {
        QCProgressDialog.showProgressDialog(this, "LOADING_MESSAGE")
        val service: ApiInterface = RetrofitUtil.retrofit(Constant.BASE_URL)!!
        val call: Call<DateResponse?>? = service.getDetails("DEMO_KEY")
        call?.enqueue(object : Callback<DateResponse?> {
            override fun onResponse(
                call: Call<DateResponse?>?,
                response: Response<DateResponse?>
            ) {
                if (response.isSuccessful) {
                    try {
                        val r: DateResponse? = response.body()

                        binding?.tvTitle?.text = r?.getTitle()
                        binding?.tvDescription?.text = r?.getExplanation()
                        Glide.with(baseContext).load(r?.getHdurl()).dontAnimate()
                            .listener(object : RequestListener<Drawable?> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    binding?.ivUrl?.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    binding?.ivUrl?.visibility = View.VISIBLE
                                    return false
                                }
                            }).into(binding?.ivUrl!!)


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                QCProgressDialog.hideProgressDialog()
            }


            override fun onFailure(call: Call<DateResponse?>, t: Throwable) {
                QCProgressDialog.hideProgressDialog()
                val str: String = RetrofitError.showErrorMessage(t)!!

            }
        })

    }

}
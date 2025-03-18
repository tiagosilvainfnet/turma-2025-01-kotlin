package dev.tiagosilva.taskmanager.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import dev.tiagosilva.taskmanager.R

class WeatherFragment : Fragment() {
    data class WeatherData(val temperatur: Double, val description: String, val condition: Int)
    data class DistrictDate(val city: String, val district: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false);

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLocation(view);
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
        }

        return view;
    }

    private fun updateUi(weatherData: WeatherData, cityDistrict: DistrictDate, view: View) {
        try {
            val imageView = view.findViewById<ImageView>(R.id.image_view)
            imageView.setImageResource(R.drawable.wi_rain);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLocation(view: View) {
//        updateUi(view);
    }
}
package com.example.mytugas_api

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytugas_api.databinding.ActivityMainBinding
import com.example.mytugas_api.model.Duck
import com.example.mytugas_api.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    // Menggunakan lazy initialization untuk menghubungkan layout binding
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val dogList = ArrayList<String>() // Menginisialisasi ArrayList untuk menyimpan URL gambar anjing
    private lateinit var adapter: DataAdapter // Adapter khusus untuk menampilkan data dalam RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        fetchDogImages(9)
    }

    private fun setupRecyclerView() {
        // Inisialisasi adapter dengan data dari dogList dan menentukan aksi saat item diklik
        adapter = DataAdapter(dogList) { imageUrl ->
            Toast.makeText(this@MainActivity, "Anda mengklik: $imageUrl", Toast.LENGTH_SHORT).show()
        }

        // Mengatur RecyclerView untuk menampilkan data dengan tata letak grid 3 kolom
        binding.rvData.apply {
            adapter = this@MainActivity.adapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }
    }

    private fun fetchDogImages(count: Int) {
        // Mendapatkan instance dari ApiClient
        val client = ApiClient.getInstance()

        // Mengulangi proses pengambilan data sebanyak jumlah yang ditentukan
        repeat(count) {
            client.getRandomDuckImage().enqueue(object : Callback<Duck> {
                // Callback ketika respons API berhasil
                override fun onResponse(call: Call<Duck>, response: Response<Duck>) {
                    if (response.isSuccessful && response.body() != null) {
                        val dogs = response.body()
                        dogs?.picture?.let {
                            dogList.add(it)
                            adapter.notifyItemInserted(dogList.size - 1) // Memberi tahu adapter ada item baru
                        }
                    } else {
                        Log.e("MainActivity", "Response failed: ${response.errorBody()?.string()}")
                        Toast.makeText(this@MainActivity, "Gagal memuat gambar", Toast.LENGTH_SHORT).show()
                    }
                }

                // Callback ketika ada kegagalan dalam permintaan jaringan
                override fun onFailure(call: Call<Duck>, t: Throwable) {
                    Log.e("MainActivity", "Network request failed: ${t.message}")
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

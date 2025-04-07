package dev.tiagosilva.taskmanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView
import dev.tiagosilva.taskmanager.utils.AuthUtils
import dev.tiagosilva.taskmanager.utils.Navigation
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CAMERA = 0
    private val PERMISSION_REQUEST_MEDIA = 1
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db_ref = FirebaseDatabase.getInstance().getReference("users/${uid}/data")
    var _image: Bitmap? = null;

    companion object {
        private const val REQUEST_IMAGE_CAMERA = 1
        private const val REQUEST_IMAGE_MEDIA = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val homeBtn = findViewById<ImageView>(R.id.home)
        val logoutBtn = findViewById<ImageView>(R.id.logout)
        val saveBtn = findViewById<Button>(R.id.save_btn)
        val cameraAction = findViewById<ImageView>(R.id.camera_action)
        val galleryAction = findViewById<ImageView>(R.id.gallery_action)

        findViewById<EditText>(R.id.email_input).setText(FirebaseAuth.getInstance().currentUser?.email);

        saveBtn.setOnClickListener {
            saveProfile()
        }

        homeBtn.setOnClickListener {
            Navigation.goToScreen(this, MainActivity::class.java)
        }

        logoutBtn.setOnClickListener {
            AuthUtils.logout(this);
        }

        cameraAction.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
        }

        galleryAction.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_MEDIA);
        }
        requestPermissions()
        loadData()
    }

    fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_REQUEST_MEDIA)
        }
    }

    fun saveProfile() {
        val usernameInput = findViewById<EditText>(R.id.username_input);
        val nameInput = findViewById<EditText>(R.id.name_input);
        val phoneInput = findViewById<EditText>(R.id.phone_input);
        val baos = ByteArrayOutputStream();
        this._image?.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val data = baos.toByteArray();
        val base64String = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);

        val db_ref = FirebaseDatabase.getInstance().getReference("users/${uid}/data/")
        db_ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var profile = HashMap<String, String>();
                if(snapshot.exists()) {
                    profile = snapshot.value as HashMap<String, String>;
                }

                profile["username"] = usernameInput.text.toString()
                profile["name"] = nameInput.text.toString()
                profile["phone"] = phoneInput.text.toString()
                profile["image"] = base64String
                db_ref.setValue(profile)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        Toast.makeText(this, R.string.profile_updated, Toast.LENGTH_SHORT).show()
        android.os.Handler().postDelayed({
            Navigation.goToScreen(this, MainActivity::class.java)
        }, 5000)
    }

    fun loadData() {
        db_ref.addListenerForSingleValueEvent(object: ValueEventListener {
            val ctx = this@ProfileActivity

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.exists()) return;
                    findViewById<EditText>(R.id.username_input).setText(snapshot.child("username").value.toString())
                    findViewById<EditText>(R.id.name_input).setText(snapshot.child("name").value.toString())
                    findViewById<EditText>(R.id.phone_input).setText(snapshot.child("phone").value.toString())

                    val image = snapshot.child("image").value.toString()
                    val decodedString = android.util.Base64.decode(image, android.util.Base64.DEFAULT)
                    val decodedByte = android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    findViewById<CircleImageView>(R.id.profile_image).setImageBitmap(decodedByte)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(ctx, R.string.error_loading_task, Toast.LENGTH_SHORT).show()
                }
            })
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ){
        super.onActivityResult(requestCode, resultCode, data);
        val profileImage = findViewById<CircleImageView>(R.id.profile_image)

        if(resultCode == RESULT_OK) {
            var imageBitmap: Bitmap? = null;
            when(requestCode) {
                REQUEST_IMAGE_CAMERA -> {
                    imageBitmap = data?.extras?.get("data") as Bitmap;
                }
                REQUEST_IMAGE_MEDIA -> {
                    val selectedImage: Uri? = data?.data
                    if (selectedImage != null) {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage);
                    }
                }
            }
            profileImage.setImageBitmap(imageBitmap)
            this._image = imageBitmap
        } else {
            Toast.makeText(this, R.string.error_to_capture_image, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.permission_refused, Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PERMISSION_REQUEST_MEDIA){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.permission_refused, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
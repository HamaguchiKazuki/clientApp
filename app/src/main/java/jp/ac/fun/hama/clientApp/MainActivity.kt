package jp.ac.`fun`.hama.clientApp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import jp.ac.`fun`.hama.serverapp.ISeyanaService
import kotlinx.android.synthetic.main.activity_main.*
//import jp.ac.fun.hama.serverapp.ISeyanaService


class MainActivity : AppCompatActivity() {
    private val tag: String = this::class.java.simpleName

    var iSeyanaService: ISeyanaService? = null
    val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iSeyanaService = ISeyanaService.Stub.asInterface(service)
            Log.d(tag, "Service connected status ${iSeyanaService}")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(tag, "Service has unexpectedly disconnected")
            iSeyanaService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onStart() {
        super.onStart()

        if (iSeyanaService == null) {
            Intent("Seyana").also { intent ->
                intent.setPackage("jp.ac.fun.hama.serverapp")
                Log.d(tag, "bind")
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
            }
        }
        button_seyana_gacha.setOnClickListener {
            val seyanaGachaText = iSeyanaService?.returnFixedLetter()
            Log.d(tag, "return text, ${seyanaGachaText}")
            textView.append(seyanaGachaText)
        }

        button_seyana_web.setOnClickListener {
            iSeyanaService?.streamSeyana()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnection)
    }
}

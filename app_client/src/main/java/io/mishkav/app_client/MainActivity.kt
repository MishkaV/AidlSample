package io.mishkav.app_client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import io.mishkav.aidl.Calculator
import io.mishkav.app_client.serviceException.ServiceException

/**
 * Класс MainActivity предназначен для отправки запроса и приема(отображения) результата
 * функции sum
 *
 * @author Ворожцов Михаил
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.text_view).setOnClickListener {
            val res = calculatorService?.sum(2, 2)
            Toast.makeText(this@MainActivity, res.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(createIntent(), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

    private fun createIntent(): Intent {
        val intent = Intent(ACTION)
        val services = packageManager.queryIntentServices(intent, 0)

        if (services.isEmpty())
            throw ServiceException(ERROR_MES)

        return Intent(intent).apply {
            component = ComponentName(
                services[0].serviceInfo.packageName,
                services[0].serviceInfo.name
            )
        }
    }

    companion object {
        private const val ACTION = "io.mishkav.aidl.REMOTE_CONNECTION"
        private const val ERROR_MES = "Services is empty"

        private var calculatorService: Calculator? = null
        private val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                calculatorService = Calculator.Stub.asInterface(p1)
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                calculatorService = null
            }
        }
    }
}
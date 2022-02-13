package io.mishkav.app_server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.mishkav.aidl.Calculator

/**
 * Реализация интерфейса AIDL внутри сервера
 *
 * @author Ворожцов Михаил
 */
class CalculatorService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return object : Calculator.Stub() {
            override fun sum(first: Int, second: Int): Int {
                return first + second
            }
        }
    }
}
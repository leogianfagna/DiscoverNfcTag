package me.leogianfagna.discovertag

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DiscoverTagActivity : AppCompatActivity() {

    // Declaração das variáveis
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private var ndefFilter: IntentFilter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtenha uma referência ao adaptador NFC (passo 3)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        // Cria um PendingIntent (usado para quando NFC descoberta, passo 4)
        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE
        )

        // Filtrar as inteções que vamos usar (passo 5)
        ndefFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)

        // Registrar os filtros (passo 6)
        val filtros = arrayOf(ndefFilter)
        val techLists = arrayOf(arrayOf(Ndef::class.java.name))

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filtros, techLists)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val dadosMensagens: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                // Finalmente: Manipulação de dados! (passo 7)
                var i: List<NdefMessage> = dadosMensagens
            }
        }
    }

    // Desregistrar os filtros (passo 8)
    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }
}
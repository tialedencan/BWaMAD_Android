package hr.ferit.tialedencan.whosaidthat_marvel

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException


class StartFragment : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_start, container, false)
        communicator = activity as Communicator

        val btnStart = view.findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener{
            val player = view.findViewById<EditText>(R.id.etPlayerNickname)

            val main = MainActivity()
            val database = main.db.collection("players")
            var guessPlayer : Player

            database.document(player.text.toString()).addSnapshotListener(object : EventListener<DocumentSnapshot?> {

                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (value != null) {
                        if (value.exists()) {
                            //update
                            guessPlayer = value.toObject(Player::class.java)!!
                            communicator.playGame(guessPlayer.nickname.toString())
                        } else {
                            //Insert
                            guessPlayer = Player(
                                player.text.toString(),
                                0,
                            )
                            database.document(player.text.toString()).set(guessPlayer)
                            communicator.playGame(guessPlayer.nickname.toString())
                        }
                    }
                }
            })
        }
        return view
    }

}
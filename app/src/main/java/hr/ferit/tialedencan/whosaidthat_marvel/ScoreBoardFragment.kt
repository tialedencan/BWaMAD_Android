package hr.ferit.tialedencan.whosaidthat_marvel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class ScoreBoardFragment : Fragment() {

    private lateinit var communicator:Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_score_board, container, false)

        //treba mi nekakav content kada dođem na fragment da popuni recycler view podacima iz baze (bez ikakvog pritiska gumba)
        communicator = activity as Communicator

        val recyclerView = view.findViewById<RecyclerView>(R.id.playersList)
        communicator.contactDatabase(recyclerView)

        val btnExit = view.findViewById<ImageButton>(R.id.btnExit)
        btnExit.setOnClickListener{
            val fragment = communicator.getFragmentWithPlayerName(GameFragment(),arguments?.getString("PLAYER").toString())
            communicator.replaceFragment(fragment)
        }
        return view
    }

}
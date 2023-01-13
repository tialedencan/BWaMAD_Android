package hr.ferit.tialedencan.whosaidthat_marvel

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

interface Communicator {
    fun playGame(playerId:String)
    fun replaceFragment(fragment: Fragment)
    fun contactDatabase(recyclerView:RecyclerView)
    fun getFragmentWithPlayerName(fragment: Fragment, playerName: String):Fragment
}
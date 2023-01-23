package hr.ferit.tialedencan.whosaidthat_marvel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayersRecyclerAdapter(
    val players:ArrayList<Player>,
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlayerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is PlayerViewHolder -> { holder.bind(players[position]) }
        }
    }

    override fun getItemCount(): Int {
       return players.size
    }

    class PlayerViewHolder(view:View):RecyclerView.ViewHolder(view){
        private var nickname = view.findViewById<TextView>(R.id.tvPlayer)
        private val points = view.findViewById<TextView>(R.id.tvPoints)

        fun bind( player:Player) {
            nickname.text = player.nickname
            points.text = player.points.toString()

        }
    }

}



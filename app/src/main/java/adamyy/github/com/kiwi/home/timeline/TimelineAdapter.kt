package adamyy.github.com.kiwi.home.timeline

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.entity.Tweet
import adamyy.github.com.kiwi.databinding.TweetCellBinding
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide

class TimelineAdapter(val timeline: MutableList<Tweet> = mutableListOf()): RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val binding = DataBindingUtil.inflate<TweetCellBinding>(LayoutInflater.from(parent.context), R.layout.cell_tweet, parent, false)
        return TimelineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val tweet = timeline[position]
        holder.setData(tweet)
    }

    override fun getItemCount(): Int = timeline.size

    fun appendTweets(tweets: List<Tweet>) {
        timeline.addAll(tweets)
        notifyDataSetChanged()
    }

    class TimelineViewHolder(val binding: TweetCellBinding): RecyclerView.ViewHolder(binding.root) {

        fun setData(tweet: Tweet) {
            binding.apply {
                screenName.text = tweet.user.screenName
                name.text = "@${tweet.user.name}"
                tweetBody.text = tweet.text
                like.text = tweet.favoriteCount.toString()
                retweet.text = tweet.retweetCount.toString()
                Glide.with(root.context)
                        .load(tweet.user.profileImageUrl)
                        .into(profileImage)
            }
        }
    }
}
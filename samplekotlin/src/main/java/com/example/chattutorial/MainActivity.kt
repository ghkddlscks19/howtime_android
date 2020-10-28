package com.example.chattutorial

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.getstream.sdk.chat.Chat
import com.getstream.sdk.chat.viewmodel.channels.ChannelsViewModel
import com.getstream.sdk.chat.viewmodel.channels.bindView
import com.getstream.sdk.chat.viewmodel.factory.ChannelsViewModelFactory
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // step 1 - setup the Chat Client
        Chat.Builder(apiKey = "b67pax5b2wdq", context = applicationContext)
            .apply { chatLogLevel = ChatLogLevel.ALL }
            .build()

        val user = User("summer-brook-2").apply {
            extraData["name"] = "Paranoid Android"
            extraData["image"] = "https://bit.ly/2TIt8NR"
        }
        // step 2 - Authenticate and connect the user
        Chat.instance().setUser(
            user = user,
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoic3VtbWVyLWJyb29rLTIifQ.CzyOx8kgrc61qVbzWvhV1WD3KPEo5ZFZH-326hIdKz0"
        )

        // step 3 - set the channel list filter and order
        val filter = Filters.and(
            Filters.eq("type", "messaging"),
            Filters.`in`("members", listOf(user.id))
        )

        val viewModel: ChannelsViewModel by viewModels {
            ChannelsViewModelFactory(
                filter,
                ChannelsViewModel.DEFAULT_SORT
            )
        }

        // step 4 -  connect the ChannelsViewModel to the channelsView, loose coupling make it easy to customize
        viewModel.bindView(channelsView, this)
        channelsView.setOnChannelClickListener { channel ->
            // open the channel activity
            startActivity(ChannelActivity4.newIntent(this, channel))
        }
    }
}

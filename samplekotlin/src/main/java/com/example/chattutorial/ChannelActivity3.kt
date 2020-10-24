package com.example.chattutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.getstream.sdk.chat.viewmodel.ChannelHeaderViewModel
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.bindView
import com.getstream.sdk.chat.viewmodel.factory.ChannelViewModelFactory
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import com.getstream.sdk.chat.viewmodel.messages.bindView
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.ChatDomain
import kotlinx.android.synthetic.main.activity_channel_3.*

class ChannelActivity3 : AppCompatActivity(R.layout.activity_channel_3) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cid = checkNotNull(intent.getStringExtra(CID_KEY)) {"Specifying a channel id is required when starting ChannelActivity"}

        // step 1 - we create 3 separate ViewModels for the views so it's easy to customize one of the components
        val viewModelProvider = ViewModelProvider(this, ChannelViewModelFactory(cid))
        val channelHeaderViewModel = viewModelProvider.get(ChannelHeaderViewModel::class.java)
        val messageListViewModel = viewModelProvider.get(MessageListViewModel::class.java)
        val messageInputViewModel = viewModelProvider.get(MessageInputViewModel::class.java)

        // step 2 = we bind the view and ViewModels. they are loosely coupled so its easy to customize
        messageListViewModel.bindView(messageListView, this)
        messageInputViewModel.bindView(messageInputView, this)

        // Set custom AttachmentViewHolderFactory
        messageListView.setAttachmentViewHolderFactory(MyAttachmentViewHolderFactory())

        // step 3 - let the message input know when we open a thread
        messageListViewModel.mode.observe(this) {
            when (it) {
                is MessageListViewModel.Mode.Thread -> messageInputViewModel.setActiveThread(it.parentMessage)
                is MessageListViewModel.Mode.Normal -> messageInputViewModel.resetThread()
            }
        }
        // step 4 - let the message input know when we are editing a message
        messageListView.setOnMessageEditHandler {
            messageInputViewModel.editMessage.postValue(it)
        }

        val typingObserver = Observer<List<User>> { users ->
            channelHeaderSub.text = if (users.isNotEmpty()) {
                "typing: " + users.joinToString(", ") { it.extraData["name"] as String }
            } else {
                "nobody is typing"
            }
        }

        ChatDomain.instance().useCases.watchChannel(cid, messageLimit = 30).enqueue {
            if (it.isSuccess) {
                val channelController = it.data()
                runOnUiThread {
                    channelController.typing.observe(this, typingObserver)
                }

            }
        }

        // step 5 - handle back button behaviour correctly when you're in a thread
        val backButtonHandler = {
            messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backButtonHandler()
                }
            }
        )
    }

    companion object {
        private const val CID_KEY = "key:cid"

        fun newIntent(context: Context, channel: Channel) =
            Intent(context, ChannelActivity::class.java).apply {
                putExtra(CID_KEY, channel.cid)
            }
    }
}

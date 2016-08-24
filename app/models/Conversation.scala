package models

/**
  * Created by victoria on 20/08/16.
  */
case class Conversation (creator: String,
                         creation_time: String,
                         message_count: String,
                         messages: IndexedSeq[Message])

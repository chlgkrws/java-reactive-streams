package com.reactivestreams

import java.util.LinkedList
import java.util.concurrent.Flow
import java.util.concurrent.atomic.AtomicInteger

class LimitEndSubscriber<T>(
	howMuchMessagesConsume: Int
) : Flow.Subscriber<T> {
	private val howMuchMessagesConsume = AtomicInteger(howMuchMessagesConsume)
	private lateinit var subscription: Flow.Subscription
	val consumedElements: MutableList<T> = LinkedList()

	override fun onSubscribe(subscription: Flow.Subscription) {
		this.subscription = subscription
		this.subscription.request(1)
	}

	override fun onNext(item: T) {
		howMuchMessagesConsume.decrementAndGet()
		println("Got: $item")
		consumedElements.add(item)
		if (howMuchMessagesConsume.get() > 0) {
			subscription.request(1)
		}
	}

	override fun onError(throwable: Throwable?) {
		throwable?.printStackTrace()
	}

	override fun onComplete() {
		println("All processing done")
	}
}
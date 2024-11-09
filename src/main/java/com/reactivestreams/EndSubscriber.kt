package com.reactivestreams

import java.util.LinkedList
import java.util.concurrent.Flow

class EndSubscriber<T> : Flow.Subscriber<T> {
	private lateinit var subscription: Flow.Subscription
	val consumedElements: MutableList<T> = LinkedList()

	override fun onSubscribe(subscription: Flow.Subscription) {
		this.subscription = subscription
		this.subscription.request(1)
	}

	override fun onNext(item: T) {
		println("Got item: $item")
		consumedElements.add(item)
		subscription.request(1);
	}

	override fun onError(throwable: Throwable?) {
		throwable?.printStackTrace()
	}

	override fun onComplete() {
		println("All processing done")
	}
}
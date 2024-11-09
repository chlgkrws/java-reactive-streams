package com.reactivestreams

import java.util.concurrent.Flow
import java.util.concurrent.SubmissionPublisher

class TransformProcessor<T, R>(
	private val transformer: (T) -> R,
) : SubmissionPublisher<R>(), Flow.Processor<T, R> {
	private lateinit var subscription: Flow.Subscription
	val consumedElements = mutableListOf<T>()

	override fun onSubscribe(subscription: Flow.Subscription) {
		this.subscription = subscription
		subscription.request(1)
	}

	override fun onNext(item: T) {
		submit(transformer(item))
		subscription.request(1)
	}

	override fun onError(t: Throwable) {
		t.printStackTrace()
	}

	override fun onComplete() {
		close()
	}
}
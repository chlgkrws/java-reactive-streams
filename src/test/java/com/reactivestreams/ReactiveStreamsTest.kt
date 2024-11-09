package com.reactivestreams

import org.junit.jupiter.api.Test
import java.util.concurrent.SubmissionPublisher
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import java.util.concurrent.TimeUnit

class ReactiveStreamsTest {

	@Test
	fun whenSubscribeToIt_thenShouldConsumeAll() {
		// given
		val publisher = SubmissionPublisher<String>()
		val subscriber = EndSubscriber<String>()
		publisher.subscribe(subscriber)
		val items = listOf("1", "x", "2", "x", "3", "x")

		// when
		assertThat(publisher.numberOfSubscribers).isEqualTo(1)
		items.forEach(publisher::submit)
		publisher.close()

		// then
		await().atMost(1000, TimeUnit.MILLISECONDS)
			.until {
				subscriber.consumedElements == items
			}

		assertThat(subscriber.consumedElements).containsExactlyElementsOf(items)
	}

	@Test
	fun `when subscribe and transform elements then should consume all`() {
		// given
		val publisher = SubmissionPublisher<String>()
		val transformProcessor = TransformProcessor<String, Int> { it.toInt() }
		val subscriber = EndSubscriber<Int>()
		val items = listOf("1", "2", "3")
		val expectedResult = listOf(1, 2, 3)

		// when
		publisher.subscribe(transformProcessor)
		transformProcessor.subscribe(subscriber)
		items.forEach(publisher::submit)
		publisher.close()

		// then
		await().atMost(1000, TimeUnit.MILLISECONDS)
			.until {
				subscriber.consumedElements == expectedResult
			}

		assertThat(subscriber.consumedElements).containsExactlyElementsOf(expectedResult)
	}

	@Test
	fun `when request for only one element then should consume one`() {
		// given
		val publisher = SubmissionPublisher<String>()
		val subscriber = LimitEndSubscriber<String>(1) // 1개의 메시지만 소비
		publisher.subscribe(subscriber)
		val items = listOf("1", "x", "2", "x", "3", "x")
		val expected = listOf("1")

		// when
		items.forEach(publisher::submit)
		publisher.close()

		// then
		await().atMost(1000, TimeUnit.MILLISECONDS).until {
			subscriber.consumedElements == expected
		}

		assertThat(subscriber.consumedElements).containsExactlyElementsOf(expected)
	}
}
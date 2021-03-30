package top.wsure.warframe.cache

import kotlinx.serialization.Serializable
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

/**
 * FileName: ExpirableCache
 * Author:   wsure
 * Date:     2021/3/29 5:16 下午
 * Description:
 */
class ExpirableCache<K, V:java.io.Serializable>(
    private val delegate: ConcurrentMap<K, CacheValue<K, V>>
) : ConcurrentMap<K, CacheValue<K, V>> by delegate {

    override val size: Int
        get() {
            recycle()
            return delegate.size
        }
    override val keys: MutableSet<K>
        get() {
            recycle()
            return delegate.keys
        }

    fun put(k: K, v: V, wait: Long?) {
        val timeout = if (wait == null) 0 else (wait + System.currentTimeMillis())
        delegate[k] = CacheValue(k, v, timeout)
    }

    override fun remove(key: K): CacheValue<K, V>? {
        recycle()
        return delegate.remove(key)
    }

    override fun get(key: K): CacheValue<K, V>? {
        recycle()
        return delegate[key]
    }

    private fun recycle() {
        val now = System.currentTimeMillis()
        delegate.values.stream()
            .filter { it.timeout != 0L && now > it.timeout }
            .map { it.key }
            .forEach { delegate.remove(it) }
    }
}
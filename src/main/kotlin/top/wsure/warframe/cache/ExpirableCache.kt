package top.wsure.warframe.cache

import java.util.concurrent.ConcurrentMap
import java.util.concurrent.TimeUnit

/**
 * FileName: ExpirableCache
 * Author:   wsure
 * Date:     2021/3/29 5:16 下午
 * Description:
 */
class ExpirableCache<K, V>(
    private val delegate: ConcurrentMap<K, CacheValue<V>>
) : ConcurrentMap<K, CacheValue<V>> by delegate {
    private var lastFlushTime = System.nanoTime()

    override val size: Int
        get() {
            recycle()
            return delegate.size
        }

    fun put(k:K,v:V,timeout:Long){
        delegate[k] = CacheValue<V>(timeout,v)
    }

    override fun remove(key: K): CacheValue<V>? {
        recycle()
        return delegate.remove(key)
    }

    override fun get(key: K): V? {
        recycle()
        return delegate[key]?.value
    }

    private fun recycle() {
        val shouldRecycle = System.nanoTime() - lastFlushTime >= TimeUnit.MILLISECONDS.toNanos(0)
        if (shouldRecycle) {
            delegate.clear()
            lastFlushTime = System.nanoTime()
        }
    }
}
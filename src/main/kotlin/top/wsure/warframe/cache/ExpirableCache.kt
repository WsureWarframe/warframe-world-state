package top.wsure.warframe.cache

import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.JsonUtils.jsonToObject
import top.wsure.warframe.utils.JsonUtils.objectToJson

/**
 * FileName: ExpirableCache
 * Author:   wsure
 * Date:     2021/3/29 5:16 下午
 * Description:
 */
class ExpirableCache<K>(
    private val delegate: MutableMap<K, CacheValue<K>>
) : MutableMap<K, CacheValue<K>> by delegate {
    companion object{
        val CACHE_MAP = CacheHolder.holder
    }
    private object CacheHolder {
        val holder = ExpirableCache(WorldStateData.cache)
    }
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

    fun put(k: K, v: Any, wait: Long?) {
        val timeout = if (wait == null) 0 else (wait + System.currentTimeMillis())
        delegate[k] = CacheValue(k, v.objectToJson(), timeout)
    }

    override fun remove(key: K): CacheValue<K>? {
        recycle()
        return delegate.remove(key)
    }

    override fun get(key: K): CacheValue<K>? {
        recycle()
        return delegate[key]
    }

    inline fun <reified T> getData(key:K):T? {
        return get(key)?.value?.jsonToObject()
    }

    private fun recycle() {
        val now = System.currentTimeMillis()
        delegate.values.stream()
            .filter { it.timeout != 0L && now > it.timeout }
            .map { it.key }
            .forEach { delegate.remove(it) }
    }
}
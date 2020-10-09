package com.sziov.smart.park.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.sziov.smart.park.constant.Constant;

/**
 * glide的缓存配置类
 */
@GlideModule
public final class GlideConfig extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .format(DecodeFormat.PREFER_RGB_565);
        // 指定位置在packageName/cache,大小为MAX_CACHE_DISK_SIZE的磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, Constant.MAX_CACHE_DISK_SIZE));
        //指定内存缓存大小
        builder.setMemoryCache(new LruResourceCache(Constant.MAX_CACHE_MEMORY_SIZE));
        //全部的内存缓存用来作为图片缓存
        builder.setBitmapPool(new LruBitmapPool(Constant.MAX_CACHE_MEMORY_SIZE));
//        builder.setLogLevel(Log.DEBUG);打印日志
        builder.setDefaultRequestOptions(options);
    }


    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}

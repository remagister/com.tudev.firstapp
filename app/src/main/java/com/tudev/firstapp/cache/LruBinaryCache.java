package com.tudev.firstapp.cache;

import android.util.LruCache;
import android.util.SparseBooleanArray;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by arseniy on 09.08.16.
 */

public class LruBinaryCache {

    private IntCache cache;
    private SparseBooleanArray array;

    private static final String LRU_BITS_STORAGE = "LRU_BITS_STORAGE";
    private static final String LRU_BITS_SIZE = "LRU_BITS_SIZE";

    private LruBinaryCache(int maxSize) {
        cache = new IntCache(maxSize);
    }

    public LruBinaryCache(SparseBooleanArray array){
        this(array.size());
        this.array = array;
    }

    public void clear(){
        cache.remove(LRU_BITS_SIZE);
        cache.remove(LRU_BITS_STORAGE);
    }

    private static int[] toInts(SparseBooleanArray array){
        int integers = array.size() / 32 + 1;
        int ret[] = new int[integers];
        Arrays.fill(ret, 0);
        int l = array.size() >= 32 ? 32 : array.size();
        for(int j = 0; j < integers; ++j) {
            for (int i = 0; i < l; ++i) {
                ret[j] = (ret[j] << 1) + (array.get(i) ? 1 : 0);
            }
        }
        return ret;
    }

    public void save(){
        if(array != null) {
            int[] toSave = toInts(array);
            cache.put(LRU_BITS_STORAGE, toSave);
            cache.put(LRU_BITS_SIZE, array.size());
        }
    }

    public SparseBooleanArray load(){
        int loaded[] = cache.get(LRU_BITS_STORAGE);
        int size = cache.getInt(LRU_BITS_SIZE);
        if(loaded == null){
            return null;
        }
        else return toSparseArray(loaded,size);
    }

    private static SparseBooleanArray toSparseArray(int[] loaded, int size) {
        SparseBooleanArray ret = new SparseBooleanArray(size);
        int counter = 0;
        outer:
        for(int i=0; i<loaded.length; ++i){
            for(int j = 0; j<32; ++j, ++counter){
                ret.put((i+1)*j, ((loaded[i] >> j) & 0x00000001) == 1);
                if(counter > size) break outer;
            }
        }
        return ret;
    }

    private class IntCache extends LruCache<String, int[]>{

        private IntCache(int maxSize) {
            super(maxSize);
        }

        private int getInt(String key){
            int ret[] = super.get(key);
            return ret == null ? -1 : ret[0];
        }

        private void put(String key, int value){
            super.put(key, new int[] {value});
        }

        @Override
        protected int sizeOf(String key, int[] value) {
            return 4 * value.length;
        }
    }
}

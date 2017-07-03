package com.ouclbc.nativeopengleswhiteboard.util;

import android.graphics.PointF;

import java.util.Iterator;

public class PointArray implements Iterable<PointF> {
        private static final int DEFAULT_CAPACITY = 128;

        float mDatas[];
        int mSize;
        private PointF mPoint = new PointF();
        private int mCapacity;

        public PointArray() {
            this(DEFAULT_CAPACITY);
        }

        public float[] getDatas(){
            return mDatas;
        }
        public PointArray(int capacity) {
            mDatas = new float[capacity];
            mCapacity = capacity;
        }

        public PointArray(PointArray pointArray) {
            this(pointArray.mCapacity);
            System.arraycopy(pointArray.mDatas, 0, mDatas, 0, pointArray.mSize);
            mSize = pointArray.mSize;
        }

        public void add(float x, float y) {
            if (mSize + 2 > mCapacity) {
                mCapacity += DEFAULT_CAPACITY;
                float datas[] = new float[mCapacity];
                System.arraycopy(mDatas, 0, datas, 0, mSize);
                mDatas = datas;
            }
            mDatas[mSize++] = x;
            mDatas[mSize++] = y;
        }

        public void add(PointF point) {
            add(point.x, point.y);
        }

        public int size() {
            return mSize >> 1;
        }

        public void clear() {
            mSize = 0;
        }

        public PointF get(int index) {
            index = index << 1;
            if (index < mSize) {
                mPoint.x = mDatas[index];
                mPoint.y = mDatas[index + 1];
                return mPoint;
            } else {
                throw new ArrayIndexOutOfBoundsException(index);
            }
        }

        public void applyNewCapacity(int capacity) {
            if (capacity > mCapacity) {
                float datas[] = new float[capacity];
                if (mSize > 0)
                    System.arraycopy(mDatas, 0, datas, 0, mSize);
                mDatas = datas;
            }
        }

        /**
         * <p>
         * Title: TODO.
         * </p>
         * <p>
         * Description: TODO.
         * </p>
         * 
         * @return
         */
        @Override
        public Iterator<PointF> iterator() {
            // TODO Auto-generated method stub
            return new PointIterator();
        }

        class PointIterator implements Iterator<PointF> {

            private int mIndex;
            private PointF mPoint = new PointF();

            /**
             * <p>
             * Title: TODO.
             * </p>
             * <p>
             * Description: TODO.
             * </p>
             * 
             * @return
             */
            @Override
            public boolean hasNext() {
                return mIndex < mSize;
            }

            /**
             * <p>
             * Title: TODO.
             * </p>
             * <p>
             * Description: TODO.
             * </p>
             * 
             * @return
             */
            @Override
            public PointF next() {
                mPoint.x = mDatas[mIndex++];
                mPoint.y = mDatas[mIndex++];
                return mPoint;
            }

            /**
             * <p>
             * Title: TODO.
             * </p>
             * <p>
             * Description: TODO.
             * </p>
             * 
             */
            @Override
            public void remove() {
                // mIndex -= 2;
                // System.arraycopy(mDatas, mIndex, mDatas, mIndex + 2, mSize
                // - mIndex - 2);
            }
        }
    }
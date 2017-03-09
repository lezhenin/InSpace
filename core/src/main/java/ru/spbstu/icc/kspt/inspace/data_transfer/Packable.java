package ru.spbstu.icc.kspt.inspace.data_transfer;


public interface Packable<T> {

    abstract class DataTransferObject<T> {}

    abstract class DataPacker<T, S extends DataTransferObject<T>> {
        public abstract S pack(T object);
        public abstract T unpack(S object);

    }

    DataPacker getPacker();

}

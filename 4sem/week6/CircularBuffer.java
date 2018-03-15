package week6;

/**
 * Single-thread circular buffer for datatype T
 * @param <T> Datatype to be stored in buffer
 */
public class CircularBuffer<T> {

    volatile private Object[] _data = null;
    volatile private int _read_index = 0;
    volatile private int _write_index = 0;
    volatile private int _capacity = 0;
    volatile private int _left_size = 0;

    public CircularBuffer(int capacity) {
        _data = new Object[capacity];
        _capacity = capacity;
        _left_size = _capacity;
    }

    public synchronized boolean push(T t) {
        if(_left_size == 0) return false;
        _data[_write_index] = t;
        if(_write_index == _capacity - 1) _write_index = 0;
        else _write_index++;
        _left_size--;
        return true;
    }

    public synchronized T pop() {
        T to_return = (T)  _data[_read_index];
        if(_read_index == _capacity - 1) _read_index = 0;
        else _read_index++;
        _left_size++;
        return to_return;
    }

    public synchronized boolean isEmpty() {
        return _left_size == _capacity;
    }

    public synchronized boolean isFull() {
        return _left_size == 0;
    }


}

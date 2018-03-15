package week6;

/**
 * Single-thread circular buffer for datatype T
 * @param <T> Datatype to be stored in buffer
 */
public class CircularBufferThread<T> {

    volatile private Object[] _data = null;
    volatile private int _read_index = 0;
    volatile private int _write_index = 0;
    volatile private int _capacity = 0;
    volatile private int _left_size = 0;

    public CircularBufferThread(int capacity) {
        _data = new Object[capacity];
        _capacity = capacity;
        _left_size = _capacity;
    }

    public synchronized boolean push(T t) {
        if(_left_size == 0) {
            try {
                this.wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        _data[_write_index] = t;
        if(_write_index == _capacity - 1) _write_index = 0;
        else _write_index++;
        _left_size--;
        this.notify();
        return true;
    }

    public synchronized T pop() {
        if(_left_size == _capacity) {
            try {
                this.wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        T to_return = (T)  _data[_read_index];
        if(_read_index == _capacity - 1) _read_index = 0;
        else _read_index++;
        _left_size++;
        this.notify();
        return to_return;
    }

    public synchronized boolean isEmpty() {
        return _left_size == _capacity;
    }

    public synchronized boolean isFull() {
        return _left_size == 0;
    }


}

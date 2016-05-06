package actor;

public interface IReceiver<T> {
	void send(T msg);
}

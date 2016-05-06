

public class Message<E extends Enum<E>> {
	public final Enum<E> type;
	public Message(Enum<E> type) { this.type=type; }
	@Override
	public String toString() {
		return "AMsg<"+type+">";
	}
}
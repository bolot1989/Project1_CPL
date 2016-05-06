package actor;

import java.util.Comparator;

public class Message implements Comparable<Message> {
	public final Enum type;
	public Message(Enum type) { this.type=type; }
	@Override
	public String toString() {
		return "AMsg<"+type+">";
	}
	@Override
	public int compareTo(Message arg0) {
		// TODO Auto-generated method stub
		return 1;
	}
}
import java.io.Serializable;

public class DLList<E> implements Serializable{
	private Node<E> head, tail, next, prev, current, node;
	private int size;
	
	@SuppressWarnings("unchecked")
	public DLList() {
		head = new Node<E>(null);
		tail = new Node<E>(null);
		head.setNext(tail);
		head.setPrev(null);
		tail.setNext(null);
		tail.setPrev(head);
		size = 0;
	}

	public void add(E data) {
		node = new Node<E>(data);
		if (size == 0) {
			head.setNext(node);
			node.setPrev(head);
			node.setNext(tail);
			tail.setPrev(node);
		}
		else {
			Node<E> end = tail.prev();
			end.setNext(node);
			node.setPrev(end);
			node.setNext(tail);
			tail.setPrev(node);
		}
		size++;
	}

	public E remove(int index) {
		current = head.next();
		E value = null;

		if (index == 0) {
			head.setNext(current.next());
			current.next().setPrev(head);
			current.setNext(null);
			current.setPrev(null);
		}
		else {
			for (int i = 0; i < index - 1; i++) {
				current = current.next();
			}
		}

		if (current.next() != null) {
			value = (E) current.next().get();
			next = current.next().next();
		}


		if (next != null) {
			current.setNext(next);
			next.setPrev(current);
		}
		else {
			current.setNext(tail);
			tail.setPrev(current);
		}
		size--;
		return value;
	}

	public E remove(E data) {
		current = head.next();
		while (current != tail) {
			if (current.get().equals(data)) {
				E value = (E) current.next().get();
				next = current.next().next();
				current.setNext(next);
				next.setPrev(current);
				return value;
			}
			else {
				current = current.next();
			}
		}
		size--;
		return null;
	}

	public void clear() {
		for (int i = 0; i < size; i++) {
			this.remove(i);
		}
	}

	public void set(int index, E data) {
		node = new Node<E>(data);
		current = head.next();
		for (int i = 0; i <index; i++) {
			current = current.next();
		}
		current.setData(data);
	}

	public Node<E> getNode(int index) {
		current = head.next();
		for (int i = 0; i <index; i++) {
			current = current.next();
		}
		return current;
	}

	public Node<E> getHead() {return head;}

	public E get(int index) {
		return getNode(index).get();
	}

	public int size() {return size;}

	public String toString() {
		String list = "";
		current = head.next();

		while (current != tail) {
			list += (current.get() + "\n");
			current = current.next();
		}
		return list;
	}

	public boolean contains (E data) {
		current = head;
		while (current != null) {
			if (current.get() != null) {
				if (current.get().equals(data))
					return true;
			}
			current = current.next();
		}
		return false;
	}

}

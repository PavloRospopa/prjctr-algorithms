package rospopa.pavlo.lrucache;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> map;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
    }

    public int get(int key) {
        var node = map.get(key);
        if (node == null) {
            return -1;
        }

        moveToStart(node);

        return node.value;
    }

    public void put(int key, int value) {
        if (head == null) {
            head = new Node(key, value, null, null);
            tail = head;
            map.put(key, head);
            return;
        }

        if (map.containsKey(key)) {
            var node = map.get(key);
            node.value = value;
            moveToStart(node);
            return;
        }

        var node = new Node(key, value, head, null);
        head.prev = node;
        head = node;
        map.put(key, node);

        if (map.size() > capacity) {
            map.remove(tail.key);
            tail.prev.next = null;
            tail = tail.prev;
        }
    }

    private void moveToStart(Node node) {
        if (node == head) {
            return;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = head;
        head.prev = node;
        head = node;
    }

    static class Node {
        int key;
        int value;
        Node next;
        Node prev;

        public Node(int key, int value, Node next, Node prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}

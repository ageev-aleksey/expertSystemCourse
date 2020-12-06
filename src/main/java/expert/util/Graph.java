package expert.util;

import java.util.ArrayList;

/**
 * Реализация класса графа
 */
public class Graph<NodeData, LinkData> {

    class Link {
        LinkData data;
        NodeIterator<NodeData, LinkData> begin;
        NodeIterator<NodeData, LinkData> end;
    }

    class Node {
        NodeData data;
        ArrayList<Link> forwardLinks = new ArrayList<>();
        ArrayList<Link> backwardLinks = new ArrayList<>();
    }

    ArrayList<Node> nodes = new ArrayList<>();

    public NodeIterator<NodeData, LinkData> addNode(NodeData d) {
        Node n = new Node();
        n.data = d;
        nodes.add(n);
        return new NodeIterator<NodeData, LinkData>(this, nodes.size() - 1);
    }


    public NodeIterator<NodeData, LinkData> addNode() {
        return addNode(null);
    }


    public LinkIterator<NodeData, LinkData> addLink(NodeIterator<NodeData, LinkData> begin, NodeIterator<NodeData, LinkData> end, LinkData d) {
        Link forward = new Link();
        Link backward = new Link();
        forward.data = d;
        forward.begin = begin;
        forward.end = end;
        backward.data = d;
        backward.begin = end;
        backward.end = begin;
        nodes.get(begin.mNodeIndex).forwardLinks.add(forward);
        nodes.get(begin.mNodeIndex).backwardLinks.add(backward);
        return new LinkIterator<>(this, begin.mNodeIndex,  nodes.get(begin.mNodeIndex).forwardLinks.size()-1, true);
    }


    public LinkIterator<NodeData, LinkData> addLink(NodeIterator<NodeData, LinkData> begin, NodeIterator<NodeData, LinkData> end) {
        return addLink(begin, end, null);
    }


    public NodeIterator<NodeData, LinkData> root() {
        if (nodes.size() == 0) {
            return null;
        }
        return new NodeIterator<>(this, 0);
    }

    public NodeIterator<NodeData, LinkData> findNode(NodeData data) {
        int i = 0;
        for(Node el : nodes) {
            if (data.equals(el.data)) {
                return new NodeIterator<>(this, i);
            }
            i++;
        }
        return null;
    }
}

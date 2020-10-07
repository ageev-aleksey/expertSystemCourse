package expert.util;

import expert.util.exception.GraphException;

public class LinkIterator<NodeData, LinkData> {

    Graph<NodeData, LinkData> mGraph;
    int mIndexNode;
    int mIndexLink;
    boolean mIsForward;

    LinkIterator(Graph<NodeData, LinkData> graph, int index_node, int index_link, boolean isForward) {
        mGraph = graph;
        mIndexNode = index_node;
        mIndexLink = index_link;
        mIsForward = isForward;
    }


    public NodeIterator<NodeData, LinkData> nodeBegin() {
        if (mIsForward) {
            return mGraph.nodes.get(mIndexNode).forwardLinks.get(mIndexLink).begin;
        }
        return mGraph.nodes.get(mIndexNode).backwardLinks.get(mIndexLink).begin;
    }


    public NodeIterator<NodeData, LinkData> nodeEnd() {
        if (mIsForward) {
            return mGraph.nodes.get(mIndexNode).forwardLinks.get(mIndexLink).end;
        }
        return mGraph.nodes.get(mIndexNode).backwardLinks.get(mIndexLink).end;
    }


    public LinkData data() {
        if (mIsForward) {
            return mGraph.nodes.get(mIndexNode).forwardLinks.get(mIndexLink).data;
        }
        return mGraph.nodes.get(mIndexNode).backwardLinks.get(mIndexLink).data;
    }


    public boolean next()  {
        if (mIsForward) {
            if (mIndexLink >= mGraph.nodes.get(mIndexNode).forwardLinks.size() - 1) {
                return false;
            }
            mIndexLink++;
            return true;
        }

        if (mIndexLink >= mGraph.nodes.get(mIndexNode).backwardLinks.size() - 1) {
            return false;
        }
        mIndexLink++;
        return true;
    }


    public boolean back() {
        if (mIndexLink == 0) {
            return false;
        }
        mIndexLink--;
        return true;
    }

    public int numLinks() {
        if (mIsForward) {
            return mGraph.nodes.get(mIndexNode).forwardLinks.size();
        }
        return mGraph.nodes.get(mIndexNode).backwardLinks.size();
    }

    public String toString() {
        if (mIsForward) {
            return mGraph.nodes.get(mIndexNode).forwardLinks.get(mIndexLink).begin.data().toString() + "-->" + mGraph.nodes.get(mIndexNode).forwardLinks.get(mIndexLink).end.data().toString();
        }
        return mGraph.nodes.get(mIndexNode).backwardLinks.get(mIndexLink).begin.data().toString() + "-->" + mGraph.nodes.get(mIndexNode).backwardLinks.get(mIndexLink).end.data().toString();

    }
}

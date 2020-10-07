package expert.util;

import expert.util.exception.GraphException;

public class NodeIterator<NodeData, LinkData>  {

    Graph<NodeData, LinkData> mGraph;
    int mNodeIndex;

    public NodeIterator(Graph<NodeData, LinkData> graph, int node_index) {
        mGraph = graph;
        mNodeIndex = node_index;
    }


    public LinkIterator<NodeData, LinkData> links()  {
        if (mGraph.nodes.get(mNodeIndex).forwardLinks.size() == 0) {
            return null;
        }
        return new LinkIterator<NodeData, LinkData>(mGraph, mNodeIndex, 0, true);
    }

    public LinkIterator<NodeData, LinkData> reversLinks() {
        if (mGraph.nodes.get(mNodeIndex).backwardLinks.size() == 0) {
            return null;
        }
        return new LinkIterator<NodeData, LinkData>(mGraph, mNodeIndex, 0, false);
    }


    public NodeData data() {
        return mGraph.nodes.get(mNodeIndex).data;
    }


    public boolean next(){
        if (mNodeIndex >= mGraph.nodes.size() - 1) {
            return false;
        }
        mNodeIndex++;
        return true;
    }

    public boolean back() {
        if (mNodeIndex == 0) {
            return false;
        }
        mNodeIndex--;
        return true;
    }

    private void check() {
        if (mNodeIndex >= mGraph.nodes.size()) {
            throw new RuntimeException("bound of range og graph iterator");
        }
    }

    public boolean equals(Object obj) {
        if( obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NodeIterator)) {
            return false;
        }
        NodeIterator<NodeData, LinkData> other = (NodeIterator<NodeData, LinkData>) obj;

        return (mGraph != other.mGraph) && (mNodeIndex == other.mNodeIndex);
    }
}

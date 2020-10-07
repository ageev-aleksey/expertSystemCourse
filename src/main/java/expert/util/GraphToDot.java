package expert.util;

import expert.alg.LinksType;
import expert.alg.NodeType;
import expert.alg.Type;

public class GraphToDot  {
    public static <DataLink> String  convert (Graph<Type, DataLink> g) {
        NodeIterator<Type, DataLink> itr = g.root();
        if (itr == null) {
            return "digraph {}";
        }
        StringBuilder b = new StringBuilder();
        b.append("digraph {\n");
        do {
            b.append('"')
                    .append(itr.data().value)
                    .append('"')
                    .append(" [");
            if (itr.data().nodeType == NodeType.FINAL) {
                b.append(" shape=diamond ");
                b.append(" color=green ");
            } else {
                if (itr.data().linksType == LinksType.AND) {
                    b.append(" shape=box ");
                }
                switch (itr.data().isResolved) {
                    case RESOLVED:
                        b.append(" color=green ");
                        break;
                    case NO_RESOLVED:
                        b.append(" color=red ");
                        break;
                    case NONE:
                        b.append(" color=gray ");
                        break;
                }
            }
            b.append("];\n");
        } while(itr.next());

        itr = g.root();

        do {
            LinkIterator<Type, DataLink> link  = itr.links();
            if (link != null) {
                do {
                    b.append("\"")
                            .append(link.nodeBegin().data().value)
                            .append("\"")
                            .append(" -> ")
                            .append("\"")
                            .append(link.nodeEnd().data().value)
                            .append("\";\n");
                } while(link.next());
            }


        } while(itr.next());

        b.append("}");
        return b.toString();
    }
}

package expert.alg;

import expert.util.Empty;
import expert.util.Graph;
import expert.util.LinkIterator;
import expert.util.NodeIterator;
import org.w3c.dom.Node;

import java.util.*;

/**
 * Реализация алгоритма Нильсона.
 * Шаги пронумерованы в соответсвии с описанием алгритма.
 */
public class FullBruteForce {
    public static Pair<Boolean, Graph<Type, Empty>> run(Graph<Type, Empty> graph) {
        /// ШАГ 1 /////
        LinkedList<NodeIterator<Type, Empty>> openedNodes = new LinkedList<>();
        LinkedList<NodeIterator<Type, Empty>> closedNodes = new LinkedList<>();
        openedNodes.add(graph.root()); // Вершина s
        NodeIterator<Type, Empty> s = graph.root();
        Graph<Type, Empty> resolvedGraph = new Graph<>();
        NodeIterator<Type, Empty> s2 = resolvedGraph.addNode(s.data());
        while (true) { // ШАГ 2
            NodeIterator<Type, Empty> n1 = openedNodes.getFirst();
            NodeIterator<Type, Empty> n2 = resolvedGraph.findNode(n1.data());
            assert (n2 != null);
            openedNodes.removeFirst();
            closedNodes.addLast(n1);
            n1.data().value += "*";
            if (n1.data().isResolved == ResolvedFlags.RESOLVED) {
                continue;
            }
            ////// ШАГ 3 ////////////////
            LinkIterator<Type, Empty> linkItr = n1.links();
            boolean haveChildren = (linkItr != null);
            if (haveChildren) {
                ////ШАГ 8/////
                Deque<NodeIterator<Type, Empty>> resolvedChildren = new LinkedList<>(); // вершины из второго графа
                do { // ВСЕ ли дочерние вершины должны быть разрешимы?
                    openedNodes.addLast(linkItr.nodeEnd());/////////////////////////////////////////////////////////////////
                    NodeIterator<Type, Empty> child = resolvedGraph.addNode(linkItr.nodeEnd().data());
                    resolvedGraph.addLink(n2, child);
                    if (linkItr.nodeEnd().data().nodeType == NodeType.FINAL) {
                        child.data().isResolved = ResolvedFlags.RESOLVED;
                        resolvedChildren.add(child);
                    }
                } while (linkItr.next());

                // Если у узла имеются разрешимые вершины, то выполняем процедуру проверки, разрешима ли S
                // if (resolvedChildren.size() == linkItr.numLinks()) {
                if (!resolvedChildren.isEmpty()) {
                    applyNotResolving(resolvedGraph.root());
                    if (s.data().isResolved == ResolvedFlags.RESOLVED) { // ШАГ 10.
                        // УСПЕХ
                        Pair<Boolean, Graph<Type, Empty>> res = new Pair<>();
                        res.first = true;
                        res.second = resolvedGraph;
                        return res;
                    } else {
                        // ШАГ 11
                        // Изъять из списка все разрешимые вершины
                        // и вершины, родители которых разрешимы
                        Iterator<NodeIterator<Type, Empty>> itr = openedNodes.iterator();
                        while (itr.hasNext()) {
                            NodeIterator<Type, Empty> el = itr.next();
                            if (/*(el.data().isResolved == ResolvedFlags.RESOLVED) || */parentIsResolved(el)) {
                                itr.remove();
                            }
                        }
                        continue;
                    }
                } else {
                    // ни одна дочерняя врешина не является заключительной, продолжаем дальше
                    continue;
                }
            } else {
                n1.data().isResolved = ResolvedFlags.NO_RESOLVED;
                /////ШАГ 4///////////
                // вершина не имеет дочерних врешин
                // выполнить процедуру разметки разрешимых и не рархрешимых вершин
                applyNotResolving(resolvedGraph.root());
                /// ШАГ 5 ///
                if (s.data().isResolved == ResolvedFlags.NO_RESOLVED) {
                    // НЕУДАЧА
                    Pair<Boolean, Graph<Type, Empty>> res = new Pair<>();
                    res.first = false;
                    res.second = resolvedGraph;
                    return res;
                }
                /// ШАГ 6//
                // изьять из списка открытых вершин все вершины имеющи неразрешимых родителей
                Iterator<NodeIterator<Type, Empty>> itr =  openedNodes.iterator();
                while (itr.hasNext()) {
                    NodeIterator<Type, Empty> node = itr.next();
                    if (parentIsNotResolved(node)) {
                        itr.remove();
                    }
                }
                /// ШАГ 7 --goto--> шаг2////
            }
        }
    }


    /**
     * Процедура разметки неразрешимых вершин
     */
    private static void applyNotResolving(NodeIterator<Type, Empty> nodeItr) {
        // ВИДИМО В ДАННОЙ ПРОЕДУРЕ НЕОБХОДИМО ОБХОДИТЬ ТОЛЬКО ТЕ ВЕРШИНЫ ДЛЯ КОТОРЫХ ИЗВЕСТНО, РАЗРЕШИМЫ ОНИ ИЛИ НЕТ! Странно
        // Изначально для всех промежуточных вершин не известо, разрешимы они или нет. Разрешимы только финальные узлы
        // Таким образом всеравно необходимо достигать листов!
        if (nodeItr.data().nodeType == NodeType.FINAL) {
            nodeItr.data().isResolved = ResolvedFlags.RESOLVED;
            return;
        }
        boolean isResolving = false;
        List<NodeIterator<Type, Empty>> visited = new LinkedList<>();
        visited.add(nodeItr);
        nodeItr.data().isResolved = and_or_move(nodeItr, visited);
    }

    private static ResolvedFlags and_or_move(NodeIterator<Type, Empty> nodeItr, List<NodeIterator<Type, Empty>> visited) {
        // Рекурсивная процедура реализующая разметку вершин по следующим определениям:
        /*
            Разрешимость вершины:
               - Заключительные врешины разрешимы, так как они соотвествуют
               элементарным подзадачам
               - Если у вершины, не являющейся заключительной, дочерние вершины типа ИЛИ,
               то она разрешима тогда и только тогда, когда разрешима покрайней мере одна дчерняя вершина
               - Если у вершины, не являющейся заключительной, дочерние вершины типа И,
               то она разрешима тогда и только тогда, когда разрешимы все ее дочерние вершины
         */
        if ((nodeItr.links() == null) && (nodeItr.data().nodeType == NodeType.MEDIUM)) {
           // nodeItr.data().isResolved =  ResolvedFlags.NONE;
            return nodeItr.data().isResolved;
        }
        if (nodeItr.data().isResolved != ResolvedFlags.NONE) {
            return nodeItr.data().isResolved;
        }

        ResolvedFlags flag = ResolvedFlags.NONE;
        if (nodeItr.data().linksType == LinksType.OR) {
            LinkIterator<Type, Empty> link = nodeItr.links();
            int num_not_resolved = 0;
            do {
                NodeIterator<Type, Empty> child = link.nodeEnd();
                ResolvedFlags status = _applyNotResolving(child, visited);
                if(status == ResolvedFlags.RESOLVED) {
                    flag = ResolvedFlags.RESOLVED;
                    break;
                } else if (status == ResolvedFlags.NO_RESOLVED) {
                    num_not_resolved++;
                }
            } while (link.next());
            if (num_not_resolved == link.numLinks()) {
                flag = ResolvedFlags.NO_RESOLVED;
            }
        } else if (nodeItr.data().linksType == LinksType.AND) {
            LinkIterator<Type, Empty> link = nodeItr.links();
            int num_resolved = 0;
            do {
                NodeIterator<Type, Empty> child = link.nodeEnd();
                ResolvedFlags status = _applyNotResolving(child, visited);
                if(status ==  ResolvedFlags.NO_RESOLVED) {
                    flag = ResolvedFlags.NO_RESOLVED;
                    break;
                } else if (status == ResolvedFlags.RESOLVED) {
                    num_resolved++;
                }
            } while (link.next());
            if ((flag == ResolvedFlags.NONE) && (num_resolved == link.numLinks())) {
                flag = ResolvedFlags.RESOLVED;
            }
        }

        nodeItr.data().isResolved = flag;
        return flag;
    }

    private static ResolvedFlags _applyNotResolving(NodeIterator<Type, Empty> nodeItr, List<NodeIterator<Type, Empty>> visited) {
        if (nodeItr.data().nodeType == NodeType.FINAL) {
            nodeItr.data().isResolved = ResolvedFlags.RESOLVED;
            return ResolvedFlags.RESOLVED;
        }
        if (visited.contains(nodeItr)) {
            return  nodeItr.data().isResolved;
        }

        return and_or_move(nodeItr, visited);
    }

    /**
     * Процедура проверки, являются ли родители заданной вершины разрешимыми
     * @param nodeItr
     * @return
     */
    private static boolean parentIsResolved(NodeIterator<Type, Empty> nodeItr) {
        ResolvedChecker checker = new ResolvedChecker(ResolvedFlags.RESOLVED, nodeItr);
        bfs(nodeItr, new Backward<>(), checker);
        return checker.get() == ResolvedFlags.RESOLVED;
    }

    private static boolean parentIsNotResolved(NodeIterator<Type, Empty> nodeItr) {
        ResolvedChecker checker = new ResolvedChecker(ResolvedFlags.NO_RESOLVED, nodeItr);
        bfs(nodeItr, new Backward<>(), checker);
        return checker.get() == ResolvedFlags.NO_RESOLVED;
    }

    private static <NodeData, LinkData> void  bfs(NodeIterator<NodeData, LinkData> root, LinksGetter<NodeData, LinkData> linkGetter, Action<NodeData, LinkData> action) {
        Queue<NodeIterator<NodeData, LinkData>> queue = new LinkedList<>();
        queue.add(root);
        List<NodeIterator<NodeData, LinkData>> visited = new LinkedList<>();
        while (!queue.isEmpty()) {
            NodeIterator<NodeData, LinkData> el = queue.poll();
            if (visited.contains(el)) {
                continue;
            }
            LinkIterator<NodeData, LinkData> links = linkGetter.get(el);
            if (links != null) {
                do {
                    queue.add(links.nodeEnd());
                } while (links.next());
            }
            if (!action.activate(el)) {
                break;
            }
            visited.add(el);
        }
    }


}

interface Action <NodeData, LinkData> {
    boolean activate(NodeIterator<NodeData, LinkData> itr);
}

interface LinksGetter <NodeData, LinkData> {
    LinkIterator<NodeData, LinkData> get(NodeIterator<NodeData, LinkData> itr);
}

class Forward <NodeData, LinkData>  implements  LinksGetter<NodeData, LinkData> {

    @Override
    public LinkIterator<NodeData, LinkData> get(NodeIterator<NodeData, LinkData> itr) {
        return itr.links();
    }
}

class Backward <NodeData, LinkData>  implements  LinksGetter<NodeData, LinkData> {

    @Override
    public LinkIterator<NodeData, LinkData> get(NodeIterator<NodeData, LinkData> itr) {
        return itr.reversLinks();
    }
}

class ResolvedChecker implements Action<Type, Empty> {

    private ResolvedFlags isResolved = ResolvedFlags.NONE;
    private ResolvedFlags check;
    NodeIterator<Type, Empty> startedNode;
    ResolvedChecker(ResolvedFlags whatCheck, NodeIterator<Type, Empty> started)
    {
        check = whatCheck;
        startedNode = started;
    }

    @Override
    public boolean activate(NodeIterator<Type, Empty> itr) {
        if (itr.equals(startedNode)) {
            return true;
        }
        isResolved = itr.data().isResolved;
        return  itr.data().isResolved != check;
    }

    ResolvedFlags get() {
        return isResolved;
    }
}

package expert.alg;

/**
 * @brief Тип вершины, описывает какие у него связи: И - связи или ИЛИ - связи
 * А так же определяет разрешима или не разрешима вершина
 */
public class Type {
    public String value = "";
    public LinksType linksType;
    public NodeType nodeType;
    public ResolvedFlags isResolved;

    public String toString() {
        return value + " (" + isResolved + ")";
    }
}

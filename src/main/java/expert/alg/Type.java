package expert.alg;

/**
 * @brief Тип вершины, описывает какие у него связи: И - связи или ИЛИ - связи
 * А так же определяет разрешима или не разрешима вершина
 */
public class Type {
    public String value = ""; // Название вершины
    public LinksType linksType; // Тип связи И/ИЛИ
    public NodeType nodeType; // Тип узла - промежуточный или конечный
    public ResolvedFlags isResolved; // Разрешима ли вершина?

    public String toString() {
        return value + " (" + isResolved + ")";
    }
}

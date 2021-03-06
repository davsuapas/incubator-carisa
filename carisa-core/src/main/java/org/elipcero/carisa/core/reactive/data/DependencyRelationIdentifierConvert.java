package org.elipcero.carisa.core.reactive.data;

import java.util.Map;

/**
 * To convert to platform identifier (Example Cassandra)
 * @param <TRelation>
 * @param <TRelationID>
 */
public interface DependencyRelationIdentifierConvert<TRelation, TRelationID, TParentID> {

    /**
     * Convert relation entity to platform identifier
     *
     * @param relation relation
     * @return platform identifier
     */
    TRelationID convert(final TRelation relation);

    /**
     * Convert from map to platform identifier
     *
     * @param id identifier like generic map
     * @return platform identifier
     */
    TRelationID convertFromDictionary(final Map<String, Object> id);

    /**
     * Convert the foreign key of relation to parent platform identifier
     *
     * @param relation
     * @return parent identifier
     */
    TParentID convertToParent(final TRelation relation);

    /**
     * Convert the foreign key of relation from object to parent platform identifier
     *
     * @param id the generic identifier
     * @return parent identifier
     */
    default TParentID convertToParentFromObject(final Object id) {
        return (TParentID)id;
    }
}

package org.elipcero.carisa.core.reactive.data;

import lombok.NonNull;
import org.elipcero.carisa.core.data.EntityInitializer;
import org.elipcero.carisa.core.data.Relation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Manage dependency relations operations. It control the relation intermediate
 * between the one relation and many relations
 * @param <TParent> One relation
 * @param <TChild> Many relation
 * @param <TRelation> Intermediate relation
 * @param <TRelationID> Relation identifier
 */
public class MultiplyDependencyRelationImpl<TParent, TChild, TRelation extends Relation, TRelationID, TParentID>
        extends DependencyRelationImpl<TParent, TRelation, TRelationID, TParentID>
        implements MultiplyDependencyRelation<TChild, TRelation> {

    private final ReactiveCrudRepository<TChild, UUID> childRepository;

    public MultiplyDependencyRelationImpl(
            @NonNull ReactiveCrudRepository<TParent, TParentID> parentRepository,
            @NonNull ReactiveCrudRepository<TChild, UUID> childRepository,
            @NonNull DependencyRelationRepository<TRelation, TRelationID> relationRepository,
            @NonNull DependencyRelationIdentifierConvert<TRelation, TRelationID, TParentID> convertRelationId) {

        super(parentRepository, relationRepository, convertRelationId);
        this.childRepository = childRepository;
    }

    /**
     * @see MultiplyDependencyRelation
     */
    @Override
    public Mono<TChild> create(
            final DependencyRelationCreateCommand<TChild, TRelation> createCommand,
            final String errorMessage) {

        if (createCommand.getChild() instanceof EntityInitializer) {
            ((EntityInitializer)createCommand.getChild()).tryInitId();
        }
        else {
            throw new IllegalArgumentException("Child must implement EntityInitializer interface");
        }

        return this
                .createBasic(createCommand.getRelation(), errorMessage)
                .flatMap(__ -> this.childRepository.save(createCommand.getChild()));
    }

    /**
     * @see MultiplyDependencyRelation
     */
    @Override
    public Flux<TChild> getChildrenByParent(UUID parentId) {
        return this.getRelationsByParent(parentId)
                .flatMap(relation -> this.childRepository
                        .findById(relation.getChildId())
                        .switchIfEmpty(this.purge(relation)));
    }

    private Mono<TChild> purge(TRelation relation) {
        return this.relationRepository
                .deleteById(this.convertRelationId.convert(relation)).then(Mono.empty());
    }
}
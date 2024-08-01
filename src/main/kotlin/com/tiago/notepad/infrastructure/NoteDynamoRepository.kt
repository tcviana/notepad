package com.tiago.notepad.infrastructure

import com.tiago.notepad.domain.note.Note
import com.tiago.notepad.domain.note.NoteRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.query.FluentQuery
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.util.*
import java.util.function.Function

/**
 * Repositório para a entidade [Note].
 *
 * Este repositório fornece métodos CRUD padrão para a entidade [Note],
 * herdando da interface [JpaRepository] e [JpaSpecificationExecutor] para filtros
 *
 * @author tcviana
 * @since 2024-07-29
 */
@Repository
@Profile("docker")
class NoteDynamoRepository(
    private val dynamoDbClient: DynamoDbClient
) : NoteRepository {

    private val enhancedClient: DynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient)
        .build()

    private val table: DynamoDbTable<Note> = enhancedClient.table("NotesTable", TableSchema.fromBean(Note::class.java))

    override fun <S : Note?> save(entity: S & Any): S & Any {
        table.putItem(entity)
        return entity
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    override fun findAll(): MutableList<Note> {
        val results = mutableListOf<Note>()
        val scanResult = table.scan()
        scanResult.items().forEach { results.add(it) }
        return results
    }

    /**
     * Deletes the entity with the given id.
     *
     *
     * If the entity is not found in the persistence store it is silently ignored.
     *
     * @param id must not be null.
     * @throws IllegalArgumentException in case the given id is null
     */
    override fun deleteById(id: Long) {
        val key = Note().apply { this.id = id }
        table.deleteItem(key)
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be null.
     * @return true if an entity with the given id exists, false otherwise.
     * @throws IllegalArgumentException if id is null.
     */
    override fun existsById(id: Long): Boolean {
        val key = Note().apply { this.id = id }
        val note = table.getItem(key)
        return note != null
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be null.
     * @return the entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    override fun findById(id: Long): Optional<Note> {
        val key = Note().apply { this.id = id }
        val note = table.getItem(key)
        return Optional.ofNullable(note)
    }

    /**
     * Saves all given entities.
     *
     * @param entities must not be null nor must it contain null.
     * @return the saved entities; will never be null. The returned Iterable will have the same size
     * as the Iterable passed as an argument.
     * @throws IllegalArgumentException in case the given [entities][Iterable] or one of its entities is
     * null.
     * @throws OptimisticLockingFailureException when at least one entity uses optimistic locking and has a version
     * attribute with a different value from that found in the persistence store. Also thrown if at least one
     * entity is assumed to be present but does not exist in the database.
     */
    override fun <S : Note?> saveAll(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Note?> findAll(example: Example<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Note?> findAll(example: Example<S>, sort: Sort): MutableList<S> {
        TODO("Not yet implemented")
    }

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort the [Sort] specification to sort the results by, can be [Sort.unsorted], must not be
     * null.
     * @return all entities sorted by the given options
     */
    override fun findAll(sort: Sort): MutableList<Note> {
        TODO("Not yet implemented")
    }

    /**
     * Returns a [Page] of entities meeting the paging restriction provided in the [Pageable] object.
     *
     * @param pageable the pageable to request a paged result, can be [Pageable.unpaged], must not be
     * null.
     * @return a page of entities
     */
    override fun findAll(pageable: Pageable): Page<Note> {
        TODO("Not yet implemented")
    }

    /**
     * Returns a [Page] of entities matching the given [Example]. In case no match could be found, an empty
     * [Page] is returned.
     *
     * @param example must not be null.
     * @param pageable the pageable to request a paged result, can be [Pageable.unpaged], must not be
     * null.
     * @return a [Page] of entities matching the given [Example].
     */
    override fun <S : Note?> findAll(example: Example<S>, pageable: Pageable): Page<S> {
        TODO("Not yet implemented")
    }

    /**
     * Returns all entities matching the given [Specification].
     *
     * @param spec must not be null.
     * @return never null.
     */
    override fun findAll(spec: Specification<Note>): MutableList<Note> {
        TODO("Not yet implemented")
    }

    /**
     * Returns a [Page] of entities matching the given [Specification].
     *
     * @param spec must not be null.
     * @param pageable must not be null.
     * @return never null.
     */
    override fun findAll(spec: Specification<Note>, pageable: Pageable): Page<Note> {
        TODO("Not yet implemented")
    }

    /**
     * Returns all entities matching the given [Specification] and [Sort].
     *
     * @param spec must not be null.
     * @param sort must not be null.
     * @return never null.
     */
    override fun findAll(spec: Specification<Note>, sort: Sort): MutableList<Note> {
        TODO("Not yet implemented")
    }

    /**
     * Returns all instances of the type `T` with the given IDs.
     *
     *
     * If some or all ids are not found, no entities are returned for these IDs.
     *
     *
     * Note that the order of elements in the result is not guaranteed.
     *
     * @param ids must not be null nor contain any null values.
     * @return guaranteed to be not null. The size can be equal or less than the number of given
     * ids.
     * @throws IllegalArgumentException in case the given [ids][Iterable] or one of its items is null.
     */
    override fun findAllById(ids: MutableIterable<Long>): MutableList<Note> {
        TODO("Not yet implemented")
    }

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities.
     */
    override fun count(): Long {
        TODO("Not yet implemented")
    }

    /**
     * Returns the number of instances matching the given [Example].
     *
     * @param example the [Example] to count instances for. Must not be null.
     * @return the number of instances matching the [Example].
     */
    override fun <S : Note?> count(example: Example<S>): Long {
        TODO("Not yet implemented")
    }

    /**
     * Returns the number of instances that the given [Specification] will return.
     *
     * @param spec the [Specification] to count instances for, must not be null.
     * @return the number of instances.
     */
    override fun count(spec: Specification<Note>): Long {
        TODO("Not yet implemented")
    }

    /**
     * Deletes a given entity.
     *
     * @param entity must not be null.
     * @throws IllegalArgumentException in case the given entity is null.
     * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
     * a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     * present but does not exist in the database.
     */
    override fun delete(entity: Note) {
        TODO("Not yet implemented")
    }

    /**
     * Deletes by the [Specification] and returns the number of rows deleted.
     *
     *
     * This method uses [Criteria API bulk delete][jakarta.persistence.criteria.CriteriaDelete] that maps directly to
     * database delete operations. The persistence context is not synchronized with the result of the bulk delete.
     *
     *
     * Please note that [jakarta.persistence.criteria.CriteriaQuery] in,
     * [Specification.toPredicate] will be null because
     * [jakarta.persistence.criteria.CriteriaBuilder.createCriteriaDelete] does not implement
     * `CriteriaQuery`.
     *
     * @param spec the [Specification] to use for the existence check, must not be null.
     * @return the number of entities deleted.
     * @since 3.0
     */
    override fun delete(spec: Specification<Note>): Long {
        TODO("Not yet implemented")
    }

    /**
     * Deletes all instances of the type `T` with the given IDs.
     *
     *
     * Entities that aren't found in the persistence store are silently ignored.
     *
     * @param ids must not be null. Must not contain null elements.
     * @throws IllegalArgumentException in case the given ids or one of its elements is null.
     * @since 2.5
     */
    override fun deleteAllById(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    /**
     * Deletes the given entities.
     *
     * @param entities must not be null. Must not contain null elements.
     * @throws IllegalArgumentException in case the given entities or one of its entities is null.
     * @throws OptimisticLockingFailureException when at least one entity uses optimistic locking and has a version
     * attribute with a different value from that found in the persistence store. Also thrown if at least one
     * entity is assumed to be present but does not exist in the database.
     */
    override fun deleteAll(entities: MutableIterable<Note>) {
        TODO("Not yet implemented")
    }

    /**
     * Deletes all entities managed by the repository.
     */
    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    /**
     * Returns a single entity matching the given [Example] or [Optional.empty] if none was found.
     *
     * @param example must not be null.
     * @return a single entity matching the given [Example] or [Optional.empty] if none was found.
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if the Example yields more than one result.
     */
    override fun <S : Note?> findOne(example: Example<S>): Optional<S> {
        TODO("Not yet implemented")
    }

    /**
     * Returns a single entity matching the given [Specification] or [Optional.empty] if none found.
     *
     * @param spec must not be null.
     * @return never null.
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if more than one entity found.
     */
    override fun findOne(spec: Specification<Note>): Optional<Note> {
        TODO("Not yet implemented")
    }

    /**
     * Checks whether the data store contains elements that match the given [Example].
     *
     * @param example the [Example] to use for the existence check. Must not be null.
     * @return true if the data store contains elements that match the given [Example].
     */
    override fun <S : Note?> exists(example: Example<S>): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Checks whether the data store contains elements that match the given [Specification].
     *
     * @param spec the [Specification] to use for the existence check, ust not be null.
     * @return `true` if the data store contains elements that match the given [Specification] otherwise
     * `false`.
     */
    override fun exists(spec: Specification<Note>): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns entities matching the given [Example] applying the [queryFunction][Function] that defines the
     * query and its result type.
     *
     * @param example must not be null.
     * @param queryFunction the query function defining projection, sorting, and the result type
     * @return all entities matching the given [Example].
     * @since 2.6
     */
    override fun <S : Note?, R : Any?> findBy(
        example: Example<S>,
        queryFunction: Function<FluentQuery.FetchableFluentQuery<S>, R>
    ): R & Any {
        TODO("Not yet implemented")
    }

    /**
     * Returns entities matching the given [Specification] applying the `queryFunction` that defines the query
     * and its result type.
     *
     * @param spec must not be null.
     * @param queryFunction the query function defining projection, sorting, and the result type
     * @return all entities matching the given Example.
     * @since 3.0
     */
    override fun <S : Note?, R : Any?> findBy(
        spec: Specification<Note>,
        queryFunction: Function<FluentQuery.FetchableFluentQuery<S>, R>
    ): R & Any {
        TODO("Not yet implemented")
    }

    /**
     * Flushes all pending changes to the database.
     */
    override fun flush() {
        TODO("Not yet implemented")
    }

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity entity to be saved. Must not be null.
     * @return the saved entity
     */
    override fun <S : Note?> saveAndFlush(entity: S & Any): S & Any {
        TODO("Not yet implemented")
    }

    /**
     * Saves all entities and flushes changes instantly.
     *
     * @param entities entities to be saved. Must not be null.
     * @return the saved entities
     * @since 2.5
     */
    override fun <S : Note?> saveAllAndFlush(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    /**
     * Deletes the given entities in a batch which means it will create a single query. This kind of operation leaves JPAs
     * first level cache and the database out of sync. Consider flushing the [EntityManager] before calling this
     * method.
     *
     *
     * It will also NOT honor cascade semantics of JPA, nor will it emit JPA  lifecycle events.
     *
     * @param entities entities to be deleted. Must not be null.
     * @since 2.5
     */
    override fun deleteAllInBatch(entities: MutableIterable<Note>) {
        TODO("Not yet implemented")
    }

    /**
     * Deletes all entities in a batch call.
     */
    override fun deleteAllInBatch() {
        TODO("Not yet implemented")
    }

    /**
     * Deletes the entities identified by the given ids using a single query. This kind of operation leaves JPAs first
     * level cache and the database out of sync. Consider flushing the [EntityManager] before calling this method.
     *
     * @param ids the ids of the entities to be deleted. Must not be null.
     * @since 2.5
     */
    override fun deleteAllByIdInBatch(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    /**
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * [jakarta.persistence.EntityNotFoundException] on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param id must not be null.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager.getReference
     * @since 2.7
     */
    override fun getReferenceById(id: Long): Note {
        TODO("Not yet implemented")
    }

    /**
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * [jakarta.persistence.EntityNotFoundException] on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param id must not be null.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager.getReference
     * @since 2.5
     */
    override fun getById(id: Long): Note {
        TODO("Not yet implemented")
    }

    /**
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * [jakarta.persistence.EntityNotFoundException] on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param id must not be null.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager.getReference
     */
    override fun getOne(id: Long): Note {
        TODO("Not yet implemented")
    }
}
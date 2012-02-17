/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.impl.service.importing;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.importing.ImportContainer;
import org.nabucco.framework.base.facade.datatype.importing.ImportContext;
import org.nabucco.framework.base.facade.datatype.importing.ImportContextEntry;
import org.nabucco.framework.base.facade.datatype.serialization.DeserializationData;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.xml.XmlSerializer;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ImportException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;

/**
 * BasicImporter
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public abstract class BasicImporter {

    private ImportContext importContext;

    private PersistenceManager pm;

    /**
     * Creates a new instance of a BasicImporter.
     * 
     * @param pm
     * @param importContext
     */
    protected BasicImporter(PersistenceManager pm, ImportContext importContext) {
        this.importContext = importContext;
    }

    /**
     * Deletes all entities of the current owner and imports all entities contained by the given
     * ImportContainer.
     * 
     * @param importContainer
     *            the container holding all entities to import
     * @throws ImportException
     *             thrown, if an entity could not be persisted
     */
    public void handleImport(ImportContainer importContainer) throws ImportException {

        try {
            // Deserialization
            List<Datatype> deserialized = deserialize(importContainer);

            // Delete all existing entities of the importing owner
            this.deleteAll(this.getOwner());

            // Import all entities
            this.importContext.getEntries().addAll(this.importAll(deserialized));

        } catch (SerializationException ex) {
            throw new ImportException("Deserialization of ImportData failed !", ex);
        }
    }

    /**
     * Deserializes all entities contained by the given container from XML to Datatype.
     * 
     * @param importContainer
     *            the container holding all entities for deserialization
     * @return a list of deserialized Datatypes
     * @throws SerializationException
     *             thrown, if an entity could not be deserialized
     */
    protected List<Datatype> deserialize(ImportContainer importContainer) throws SerializationException {

        XmlSerializer xmlSerializer = new XmlSerializer();
        DeserializationData data = new DeserializationData(importContainer.getContent().getValue());
        data.setResourceData(importContainer.getResourceData().getValue());

        List<Datatype> deserialized = xmlSerializer.deserialize(data);
        return deserialized;
    }

    /**
     * Imports the given list of datatypes.
     * 
     * @param datatypes
     *            the list of datatypes to import
     * @return a list of ImportContextEntry
     * @throws ImportException
     *             thrown, if a datatype could not be imported
     */
    protected abstract List<ImportContextEntry> importAll(List<Datatype> datatypes) throws ImportException;

    /**
     * Deletes all entities of the given owner.
     * 
     * @param owner
     *            the owner
     * @throws ImportException
     *             thrown, if a entity could not be deleted
     */
    protected abstract void deleteAll(Owner owner) throws ImportException;

    /**
     * Sets the DatatypeState of the given NabuccoDatatype to INITIALIZED and maintains it.
     * 
     * @param datatype
     *            the NabuccoDatatype to maintain
     * @return the maintained NabuccoDatatype containing its database id
     * @throws PersistenceException
     *             thrown, if the NabuccoDatatype could not be maintained
     */
    protected NabuccoDatatype create(NabuccoDatatype datatype) throws PersistenceException {
        datatype.setDatatypeState(DatatypeState.INITIALIZED);
        datatype.setId((Identifier) null);
        return this.pm.persist(datatype);
    }

    /**
     * Sets the DatatypeState of the given NabuccoDatatype to MODIFIED and maintains it.
     * 
     * @param datatype
     *            the NabuccoDatatype to maintain
     * @return the maintained NabuccoDatatype containing its database id
     * @throws PersistenceException
     *             thrown, if the NabuccoDatatype could not be maintained
     */
    protected NabuccoDatatype modify(NabuccoDatatype datatype) throws PersistenceException {
        datatype.setDatatypeState(DatatypeState.MODIFIED);
        return this.pm.persist(datatype);
    }

    /**
     * Sets the DatatypeState of the given NabuccoDatatype to DELETED and maintains it.
     * 
     * @param datatype
     *            the NabuccoDatatype to maintain
     * @throws PersistenceException
     *             thrown, if the NabuccoDatatype could not be maintained
     */
    protected void delete(NabuccoDatatype datatype) throws PersistenceException {
        datatype.setDatatypeState(DatatypeState.DELETED);
        this.pm.persist(datatype);
    }

    /**
     * Gets the current ImportContext.
     * 
     * @return the current ImportContext
     */
    protected ImportContext getImportContext() {
        return this.importContext;
    }

    /**
     * Maintains the given entity and creates an ImportContextEntry containing the old an new id of
     * the entity.
     * 
     * @param entity
     *            the entity to maintain
     * @return the created ImportContextEntry
     * @throws ImportException
     *             thrown, if the entity could not be maintained
     */
    protected ImportContextEntry maintain(NabuccoDatatype entity) throws ImportException {

        ImportContextEntry result = new ImportContextEntry();
        result.setOldId(entity.getId());
        result.setTypeName(entity.getClass().getName());

        try {
            entity = create(entity);
        } catch (PersistenceException ex) {
            throw new ImportException("Unable to import " + entity.getClass().getSimpleName(), ex);
        }
        result.setNewId(entity.getId());
        return result;
    }

    /**
     * Gets the new database id for a certain class and old id from the current ImportContext.
     * 
     * @param oldId
     *            the old id
     * @param className
     *            the classname
     * @return the new id
     */
    protected Long getNewRefId(Long oldId, String className) {

        for (ImportContextEntry entry : this.importContext.getEntries()) {

            if (entry.getTypeName().getValue().equals(className) && entry.getOldId().getValue().equals(oldId)) {
                return entry.getNewId().getValue();
            }
        }
        return null;
    }

    /**
     * Gets the current owner.
     * 
     * @return the current owner
     */
    protected Owner getOwner() {
        return this.getImportContext().getOwner();
    }

    /**
     * Gets the current PersistenceManager;
     * 
     * @return the current PersistenceManager
     */
    protected PersistenceManager getPersistenceManager() {
        return this.pm;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shape;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
/**
 *
 * @author Home
 */

public class OntologyLoader {
    private OWLOntology ontology;
    
    public OntologyLoader(String ontologyPath) throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        this.ontology = manager.loadOntologyFromOntologyDocument(IRI.create(ontologyPath));
    }
    
    public OWLOntology getOntology() {
        return ontology;
    }
}
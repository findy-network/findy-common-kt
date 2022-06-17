package org.findy_network.findy_common_kt

import io.grpc.CallCredentials.*
import io.grpc.ManagedChannel
import org.findy_network.findy_common_kt.ProtocolServiceGrpcKt.ProtocolServiceCoroutineStub

class ProofRequestAttribute(val name: String, val credDefId: String)

class ProtocolClient(private val channel: ManagedChannel, private val token: String) {
  private val stub: ProtocolServiceCoroutineStub =
      ProtocolServiceCoroutineStub(channel).withCallCredentials(Creds(token = token))

  suspend fun status(id: String): ProtocolStatus {
    return stub.status(ProtocolID.newBuilder().setID(id).build())
  }

  suspend fun connect(invitationURL: String, label: String): ProtocolID {
    return stub.start(
        Protocol.newBuilder()
            .setTypeID(Protocol.Type.DIDEXCHANGE)
            .setRole(Protocol.Role.INITIATOR)
            .setDIDExchange(
                Protocol.DIDExchangeMsg.newBuilder()
                    .setInvitationJSON(invitationURL)
                    .setLabel(label))
            .build())
  }

  suspend fun sendMessage(connectionId: String, message: String): ProtocolID {
    return stub.start(
        Protocol.newBuilder()
            .setTypeID(Protocol.Type.BASIC_MESSAGE)
            .setRole(Protocol.Role.INITIATOR)
            .setConnectionID(connectionId)
            .setBasicMessage(Protocol.BasicMessageMsg.newBuilder().setContent(message))
            .build())
  }

  suspend fun sendCredentialOffer(
      connectionId: String,
      attributes: Map<String, String>,
      credDefId: String,
  ): ProtocolID {
    val credAttributes =
        attributes.map {
          Protocol.IssuingAttributes.Attribute.newBuilder()
              .setName(it.key)
              .setValue(it.value)
              .build()
        }

    return stub.start(
        Protocol.newBuilder()
            .setTypeID(Protocol.Type.ISSUE_CREDENTIAL)
            .setRole(Protocol.Role.INITIATOR)
            .setConnectionID(connectionId)
            .setIssueCredential(
                Protocol.IssueCredentialMsg.newBuilder()
                    .setCredDefID(credDefId)
                    .setAttributes(
                        Protocol.IssuingAttributes.newBuilder()
                            .addAllAttributes(credAttributes)
                            .build()))
            .build())
  }

  suspend fun sendProofRequest(
      connectionId: String,
      attributes: List<ProofRequestAttribute>,
  ): ProtocolID {
    val proofAttributes =
        attributes.map {
          Protocol.Proof.Attribute.newBuilder().setName(it.name).setCredDefID(it.credDefId).build()
        }

    return stub.start(
        Protocol.newBuilder()
            .setTypeID(Protocol.Type.PRESENT_PROOF)
            .setRole(Protocol.Role.INITIATOR)
            .setConnectionID(connectionId)
            .setPresentProof(
                Protocol.PresentProofMsg.newBuilder()
                    .setAttributes(
                        Protocol.Proof.newBuilder().addAllAttributes(proofAttributes).build()))
            .build())
  }
}

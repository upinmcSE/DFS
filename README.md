# Build Distributed File Storage System with Java

## Flow

      Node A                        Node B
     (outbound)                    (inbound)
┌────────────────┐          ┌─────────────────┐
│ Dial("B:3000") │ ───────▶ │ ListenAndAccept │
└────────────────┘          └─────────────────┘
       │                            │
       ▼                            ▼
┌────────────────┐          ┌────────────────┐
│ handleConn()   │          │ handleConn()   │
│ HandshakeFunc()│ <──────▶ │ HandshakeFunc()│
└────────────────┘          └────────────────┘
       │                           │
       ▼                           ▼
    gửi RPC  ───────────────────▶ nhận RPC



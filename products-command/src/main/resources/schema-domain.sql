-- Suppression des tables existantes (dans l'ordre inverse des dépendances)
DROP TABLE IF EXISTS subscriptions CASCADE;
DROP TABLE IF EXISTS revenues CASCADE;
DROP TABLE IF EXISTS spent CASCADE;
DROP TABLE IF EXISTS sales CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS tenants CASCADE;


-- Création des tables dans l'ordre logique
CREATE TABLE IF NOT EXISTS tenants
(
  id         VARCHAR(100) PRIMARY KEY,
  name       VARCHAR(255) NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS users
(
  id         VARCHAR(100) PRIMARY KEY,
  tenant_id  VARCHAR(100), -- Nullable
  name       VARCHAR(100)        NOT NULL,
  email      VARCHAR(255) UNIQUE NOT NULL,
  password   VARCHAR(255)        NOT NULL,
  role       VARCHAR(50),
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS products
(
  id         VARCHAR(100) PRIMARY KEY,
  tenant_id  VARCHAR(100), -- Nullable
  name       VARCHAR(255)   NOT NULL,
  price      DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS sales
(
  id          VARCHAR(100) PRIMARY KEY,
  tenant_id   VARCHAR(100), -- Nullable
  product_id  VARCHAR(100), -- Nullable
  quantity    INT            NOT NULL CHECK (quantity > 0),
  total_price DECIMAL(10, 2) NOT NULL CHECK (total_price >= 0),
  created_at  TIMESTAMP DEFAULT NOW(),
  updated_at  TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS spent
(
  id          VARCHAR(100) PRIMARY KEY,
  tenant_id   VARCHAR(100), -- Nullable
  amount      DECIMAL(15, 2) NOT NULL CHECK (amount >= 0),
  description TEXT,
  created_at  TIMESTAMP DEFAULT NOW(),
  updated_at  TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS revenues
(
  id            VARCHAR(100) PRIMARY KEY,
  tenant_id     VARCHAR(100), -- Nullable
  total_revenue DECIMAL(15, 2) NOT NULL CHECK (total_revenue >= 0),
  total_spent   DECIMAL(15, 2) NOT NULL CHECK (total_spent >= 0) DEFAULT 0,
  net_profit    DECIMAL(15, 2) GENERATED ALWAYS AS (total_revenue - total_spent) STORED,
  last_updated  TIMESTAMP                                        DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS subscriptions
(
  id         VARCHAR(100) PRIMARY KEY,
  tenant_id  VARCHAR(100), -- Nullable
  plan       VARCHAR(50),
  start_date TIMESTAMP DEFAULT NOW(),
  end_date   TIMESTAMP,
  status     VARCHAR(20)
);

-- Ajout des contraintes de clé étrangère séparément
ALTER TABLE users
  ADD CONSTRAINT fk_users_tenants
    FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE CASCADE;

ALTER TABLE products
  ADD CONSTRAINT fk_products_tenants
    FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE CASCADE;

ALTER TABLE sales
  ADD CONSTRAINT fk_sales_tenants
    FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE CASCADE,
  ADD CONSTRAINT fk_sales_products
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;

ALTER TABLE spent
  ADD CONSTRAINT fk_spent_tenants
    FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE CASCADE;

ALTER TABLE revenues
  ADD CONSTRAINT fk_revenues_tenants
    FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE CASCADE;

ALTER TABLE subscriptions
  ADD CONSTRAINT fk_subscriptions_tenants
    FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE CASCADE;


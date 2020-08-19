/*
 * Copyright 2020 Precog Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package quasar.plugin.hana.destination

import scala._, Predef._

import quasar.api.destination.DestinationType
import quasar.connector.MonadResourceErr
import quasar.connector.destination.{Destination, PushmiPullyu}
import quasar.plugin.jdbc._
import quasar.plugin.jdbc.destination._

import argonaut.Json

import cats.data.NonEmptyList
import cats.effect.{ConcurrentEffect, ContextShift, Resource, Timer}
import cats.implicits._

import doobie.Transactor

import org.slf4s.Logger

object HANADestinationModule extends JdbcDestinationModule[DestinationConfig] {

  val destinationType = DestinationType("hana", 1L)

  def sanitizeDestinationConfig(config: Json): Json = scala.Predef.???

  def transactorConfig(config: DestinationConfig)
      : Either[NonEmptyList[String], TransactorConfig] = scala.Predef.???

  def jdbcDestination[F[_]: ConcurrentEffect: ContextShift: MonadResourceErr: Timer](
      config: DestinationConfig,
      transactor: Transactor[F],
      pushPull: PushmiPullyu[F],
      log: Logger)
      : Resource[F, Either[InitError, Destination[F]]] =
    (new HANADestination[F](config.writeMode, transactor, log): Destination[F])
      .asRight[InitError]
      .pure[Resource[F, ?]]
}
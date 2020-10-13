/*
 * Copyright 2020 Kato Shinya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.thinkit.generator.common.markdown.content.entity;

import java.io.Serializable;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.thinkit.framework.content.entity.ContentEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 各生成器で生成したリソースを出力する際の既定出力先を管理するデータクラスです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public final class DefaultOutputPath implements ContentEntity, Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 5538480240722962153L;

    /**
     * 環境変数名
     */
    @Getter
    private String environmentVariableName;

    /**
     * 出力先ディレクトリ
     */
    @Getter
    private String outputDirectory;

    /**
     * デフォルトコンストラクタ
     */
    private DefaultOutputPath() {
    }

    /**
     * コンストラクタ
     *
     * @param environmentVariableName 環境変数名
     * @param outputDirectory         出力先ディレクトリ
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private DefaultOutputPath(@NonNull String environmentVariableName, @NonNull String outputDirectory) {
        this.environmentVariableName = environmentVariableName;
        this.outputDirectory = outputDirectory;
    }

    /**
     * コピーコンストラクタ
     *
     * @param defaultOutputPath 既定出力先オブジェクト
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private DefaultOutputPath(@NonNull DefaultOutputPath defaultOutputPath) {
        this.environmentVariableName = defaultOutputPath.getEnvironmentVariableName();
        this.outputDirectory = defaultOutputPath.getOutputDirectory();
    }

    /**
     * 引数として指定された情報を基に {@link DefaultOutputPath} クラスの新しいインスタンスを生成し返却します。
     *
     * @param environmentVariableName 環境変数名
     * @param outputDirectory         出力先ディレクトリ
     * @return {@link DefaultOutputPath} クラスの新しいインスタンス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static DefaultOutputPath of(@NonNull String environmentVariableName, @NonNull String outputDirectory) {
        return new DefaultOutputPath(environmentVariableName, outputDirectory);
    }

    /**
     * 引数として指定された {@code defaultOutputPath} オブジェクトの情報を基に {@link DefaultOutputPath}
     * クラスの新しいインスタンスを生成し返却します。
     *
     * @param defaultOutputPath 既定出力先オブジェクト
     * @return {@link DefaultOutputPath} クラスの新しいインスタンス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public static DefaultOutputPath of(@NonNull DefaultOutputPath defaultOutputPath) {
        return new DefaultOutputPath(defaultOutputPath);
    }
}
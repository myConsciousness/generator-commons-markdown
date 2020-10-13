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

package org.thinkit.generator.common.markdown;

import com.google.common.flogger.FluentLogger;

import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.thinkit.common.catalog.Delimiter;
import org.thinkit.common.util.file.FluentFile;
import org.thinkit.generator.common.Generator;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 定義書の解析処理における基底クラスです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public abstract class AbstractGenerator implements Generator {

    /**
     * ログ出力オブジェクト
     */
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    /**
     * 生成する定義のパスを管理するオブジェクト
     */
    @Getter(AccessLevel.PRIVATE)
    private DefinitionPath definitionPath = null;

    /**
     * デフォルトコンストラクタ
     */
    @SuppressWarnings("unused")
    private AbstractGenerator() {
    }

    /**
     * コンストラクタ
     *
     * @param definitionPath 生成する定義のパスを管理するオブジェクト
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    protected AbstractGenerator(@NonNull DefinitionPath definitionPath) {
        this.definitionPath = definitionPath;
    }

    /**
     * メイン処理を定義する抽象メソッドです。
     *
     * @return 処理が正常終了した場合は {@code true} 、それ以外は {@code false}
     */
    protected abstract boolean run();

    @Override
    public boolean execute() {

        try {
            if (!this.run()) {
                return false;
            }
        } catch (Exception e) {
            logger.atSevere().log("実行時に想定外のエラーが発生しました。");
            logger.atSevere().log("ログを解析し原因調査と修正を行ってください。");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 定義書へのファイルパスを返却します。
     *
     * @return 定義書へのファイルパス
     */
    protected String getFilePath() {
        return this.definitionPath.getFilePath();
    }

    /**
     * 出力先のパスを返却します。
     *
     * @return 出力先へのパス
     */
    protected String getOutputPath() {
        return this.definitionPath.getOutputPath();
    }

    /**
     * パッケージ情報を付与した出力先のパスを返却します。<br>
     * パッケージ情報はカンマ区切りの文字列として渡してください。<br>
     * 例えば、以下のような形式で渡してください。<br>
     * 渡されたパッケージ情報はカンマの区切り文字をプラットフォームに応じたファイルセパレータに変換し出力先パスに付与します。<br>
     * <br>
     * {@code "org.thinkit.generator"}<br>
     * <br>
     * 引数として{@code null}が指定された場合は実行時に必ず失敗します。
     *
     * @param packageName カンマ区切りで表現されたパッケージ名
     * @return 出力先へのパス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    protected String getOutputPath(@NonNull String packageName) {

        if (packageName.length() <= 0) {
            return this.definitionPath.getOutputPath();
        }

        final String fileSeparator = FluentFile.getFileSeparator();

        final StringBuilder outputPath = new StringBuilder().append(this.definitionPath.getOutputPath())
                .append(fileSeparator).append(StringUtils.replace(packageName, Delimiter.period(), fileSeparator));

        return outputPath.toString();
    }
}
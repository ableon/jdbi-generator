package org.jdbi.generator.main;

import jakarta.validation.ValidationException;
import org.jdbi.generator.cli.CLI;
import org.jdbi.generator.cli.CLIArgs;
import org.jdbi.generator.cli.Console;

import java.io.File;


public class Main
{

    public static void main(String[] args)
    {
        try
        {
            Console.banner();

            if (false)
            {
                args = new String[]
                {
                    //"-h",
                    "-f",
                    "C:/Temp/jdbi-generator/manadas-access.yml",
                    //"C:/Temp/jdbi-generator/manadas-mysql.yml",
                    //"./test-postgresql.yml",
                    "-v",
                };
            }


            if (!CLI.getInstance().parseArgs(args))
                return;


            if (CLIArgs.getInstance().isHelp())
            {
                CLI.getInstance().usage();
            }
            else
            {
                String sourceFileName = CLIArgs.getInstance().getSource();
                File sourceFile = new File( sourceFileName );

                if (sourceFile.exists() && sourceFile.isFile())
                {
                    Generator generator = new Generator();
                    generator.generate( sourceFile );
                }
                else
                {
                    Console.fileNotFound( sourceFile );
                }
            }
        }
        catch (ValidationException e)
        {
            Console.validationError( e.getMessage() );
        }
        catch (Exception e)
        {
            Console.error( e.toString() );
        }
        finally
        {
            Console.separator();
        }
    }

}

